package controllers.utils

import daos.TokenDAO
import exceptions.Exceptions._
import models.Permission
import monix.eval.Task
import monix.execution.Scheduler
import play.api.data.Form
import play.api.mvc._
import services.JWTService
import services.commands.Impl._


import scala.concurrent.ExecutionContext


class ControllerUtils(cc: ControllerComponents,
                      jwtService: JWTService,
                      tokenDAO: TokenDAO,
                      checkPermissionCommand: CheckPermissionCommand)
                     (implicit ex: ExecutionContext, sch: Scheduler)
  extends AbstractController(cc) {

  def actionWithRecover(block: CustomRequest[Any] => Task[Result]): Action[AnyContent] =
    Action.async {
      req =>
        block(CustomRequest(req, None, None)).onErrorRecover {
          //401
          case UnauthorizedException => Unauthorized
          //403
          case ForbiddenException(msg) => Forbidden(msg)
          //404
          case NotFoundException(msg) => NotFound(msg)
          //409
          case UserAlreadyExistsException => Conflict
          //412
          case ParamNotPassedException(param) => PreconditionFailed(s"Param $param not passed")
          //417
          case WrongCredentials => ExpectationFailed
          case TokenBrokenOrExpired => ExpectationFailed
          //422
          case WrongJsonException(msg) => UnprocessableEntity(msg.mkString)
          case StrIsNotUUIDException => UnprocessableEntity

          //500
          case e =>
            e.printStackTrace()
            InternalServerError
        }.runToFuture
    }


  def authorizedAction[A](permission: Permission)
                         (block: CustomRequest[A] => Task[Result])(implicit form: Form[A]): Action[AnyContent] = {
    actionWithBody[A] {
      req =>
        val token = req.request.headers.get("x-auth-token").getOrElse("")
        val (userId, email) = jwtService.decodeToken(token = token)
        tokenDAO.getByUserId(userId).flatMap(_.map(_.token).contains(token) match {
          case true => checkPermissionCommand
            .execute(parameters = CheckPermissionParameters(userId = userId, permission = permission))
            .flatMap({
              case true => block(CustomRequest(request = req, userIdOption = Option(userId), bodyOption = req.bodyOption))
              case false => throw UserHasNoPermissionError
            })
            block(CustomRequest(request = req, userIdOption = Option(userId), bodyOption = req.bodyOption))
          case false => throw TokenBrokenOrExpired
        })
    }
  }

  def actionWithBody[A](block: CustomRequest[A] => Task[Result])(implicit form: Form[A]): Action[AnyContent] =
  Action.async(req => Task(CustomRequest(req,None,None).bodyAsJson).flatMap(block).runToFuture)
}
