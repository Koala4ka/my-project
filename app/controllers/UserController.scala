package controllers

import controllers.utils.ControllerUtils
import daos.TokenDAO
import models.{Permission, PermissionWrapper}
import models.dtos.question.UserUpdateQuestion
import monix.execution.Scheduler
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.{JWTService, UserService}
import services.commands.Impl.CheckPermissionCommand

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext


@Singleton
class UserController @Inject()(cc: ControllerComponents,
                               jwtService: JWTService,
                               tokenDAO: TokenDAO,
                               userService: UserService,
                               checkPermissionCommand: CheckPermissionCommand,
                              )(implicit ex: ExecutionContext,
                                sch: Scheduler)
  extends ControllerUtils(cc, jwtService, tokenDAO, checkPermissionCommand) {

  def getAll(organizationId: Option[Long]): Action[AnyContent] =
    authorizedActionGET(permissionWrapper = PermissionWrapper(permissionName = "View",
      isGlobal = true),organizationId=organizationId) { implicit request =>
      userService.getAll().map {
        users =>
          val arrayOfJS = users.map(Json.toJson(_))
          Ok(Json.toJson(arrayOfJS))
      }
    }


  def updateUser(organizationId: Option[Long]): Action[AnyContent] =
    authorizedActionPOST[UserUpdateQuestion](permissionWrapper = PermissionWrapper(permissionName = "User-Edit",
      isGlobal = true),organizationId = organizationId) {
      req =>
        userService.update(req.parsedBody)
          .map(dto => Ok(Json.toJson(dto)))
    }(UserUpdateQuestion.userUpdateQuestion)

}
