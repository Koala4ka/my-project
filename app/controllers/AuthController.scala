package controllers

import controllers.utils.ControllerUtils
import daos.TokenDAO
import models.PermissionWrapper
import models.dtos.question.{Credentials, SignUpForm}
import monix.execution.Scheduler
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.commands.Impl.CheckPermissionCommand
import services.{AuthService, JWTService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               jwtService: JWTService,
                               tokenDAO: TokenDAO,
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

  def signUp(organizationId: Option[Long]): Action[AnyContent] =
    authorizedActionPOST[SignUpForm](permissionWrapper = PermissionWrapper(permissionName = "User-Add",
      isGlobal = true),organizationId = organizationId) {
      req =>
        authService.signUp(req.parsedBody)
          .map(dto => Ok(Json.toJson(dto)))
    }(SignUpForm.signUpForm)

}



