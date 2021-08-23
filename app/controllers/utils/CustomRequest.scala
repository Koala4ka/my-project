package controllers.utils

import exceptions.Exceptions._
import play.api.data.Form
import play.api.data.FormBinding.Implicits._
import play.api.mvc.{AnyContent, Request, WrappedRequest}

import scala.util.Try

case class CustomRequest[T](request: Request[AnyContent],
                            userIdOption: Option[Long],
                            bodyOption: Option[T]) extends WrappedRequest(request) {

  lazy val parsedBody: T = bodyOption.get
  lazy val userId: Long = userIdOption.get

  def bodyAsJson[A](implicit form: Form[A]): CustomRequest[A] = {
    form.bindFromRequest()(request, formBinding).fold(
      formWithErrors => throw WrongJsonException(formWithErrors.errors),
      model => CustomRequest(request, userIdOption, bodyOption = Some(model))
    )
  }

  def withUserId: CustomRequest[T] = {
    request.session.get(CustomRequest.UserIdKey) match {
      case Some(id) if Try(id.toLong).isSuccess => CustomRequest(request, Some(id.toLong), bodyOption)
      case _ => throw UnauthorizedException
    }
  }

}

object CustomRequest {
  val UserIdKey = "userId"
}