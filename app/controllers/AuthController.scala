package controllers

import controllers.utils.{ControllerUtils, CustomRequest}
import daos.{TokenDAO, UserDAO}
import models.dtos.question.{Credentials, SignUpForm}
import monix.execution.Scheduler
import play.api.Configuration
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.{AuthService, JWTService}

import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class AuthController @Inject()(cc: ControllerComponents,
                               jwtService: JWTService,
                               tokenDAO: TokenDAO,
                               authService: AuthService)(implicit ex: ExecutionContext,
                                                         sch: Scheduler)
  extends ControllerUtils(cc, jwtService, tokenDAO) {

  def signIn: Action[AnyContent] = actionWithBody[Credentials] {
    req =>
      authService.signIn(req.parsedBody)
        .map(dto => Ok(Json.toJson(dto)))
  }(Credentials.signInForm)

  def signUp: Action[AnyContent] = authorizedAction[SignUpForm] {
    req =>
      authService.signUp(req.parsedBody)
        .map(dto => Ok(Json.toJson(dto)))
  }(SignUpForm.signUpForm)
}
