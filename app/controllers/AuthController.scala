package controllers

import controllers.utils.ControllerUtils
import daos.TokenDAO
import models.Permission
import models.dtos.question.{Credentials, SignUpForm, UserUpdateQuestion}
import monix.execution.Scheduler
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.commands.Impl.CheckPermissionCommand
import services.{AuthService, JWTService, UserService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               jwtService: JWTService,
                               tokenDAO: TokenDAO,
                               userService: UserService,
                               authService: AuthService,
                               checkPermissionCommand: CheckPermissionCommand
                              )(implicit ex: ExecutionContext,
                                sch: Scheduler)
  extends ControllerUtils(cc, jwtService, tokenDAO, checkPermissionCommand) {

  def signIn: Action[AnyContent] = actionWithBody[Credentials] {
    req =>
      authService.signIn(req.parsedBody)
        .map(dto => Ok(Json.toJson(dto)))
  }(Credentials.signInForm)

  def signUp: Action[AnyContent] =
    authorizedActionPOST[SignUpForm](permission = Permission(name = "User-Add")) {
      req =>
        authService.signUp(req.parsedBody)
          .map(dto => Ok(Json.toJson(dto)))
    }(SignUpForm.signUpForm)

  def getAll: Action[AnyContent] =
    authorizedActionGET(permission = Permission(name = "View")) { implicit request =>
      userService.getAll().map {
        users =>
          val arrayOfJS = users.map(Json.toJson(_))
          Ok(Json.toJson(arrayOfJS))
      }
    }

  def updateUser: Action[AnyContent] =
    authorizedActionPOST[UserUpdateQuestion](permission = Permission(name = "User-Edit")) {
      req =>
        userService.update(req.parsedBody)
          .map(dto => Ok(Json.toJson(dto)))
    }(UserUpdateQuestion.userUpdateQuestion)

}



