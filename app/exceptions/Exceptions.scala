package exceptions

import play.api.data.FormError

object Exceptions {
  case class WrongJsonException(errors: Seq[FormError]) extends RuntimeException(s"Wrong json.\n$errors")

  case object WrongCredentials extends RuntimeException

  case object UserRoleDoesNotExist extends RuntimeException

  case object UserHasNoPermissionError extends RuntimeException

  case object UnauthorizedException extends RuntimeException

  case class ForbiddenException(message: String) extends RuntimeException("Forbidden. " + message)

  case class NotFoundException(message: String) extends RuntimeException(message)

  object NotFoundException {
    def apply(model: String, cond: String): NotFoundException = new NotFoundException(s"$model with $cond not found!")
  }

  case class InternalException(message: String = "Internal exception") extends RuntimeException(message)

  case class DbInternalException(e: Throwable) extends RuntimeException("DB exception", e)

  case object DbResultException extends RuntimeException("No raws was affected")

  case object UserAlreadyExist extends RuntimeException

  case object UserDosNotExist extends RuntimeException

  case object TokenBrokenOrExpired extends RuntimeException

  case class MailerServiceException(implException: Throwable) extends RuntimeException(implException)

  case object StrIsNotUUIDException extends RuntimeException

  case object UserAlreadyExistsException extends RuntimeException

  case class ParamNotPassedException(param: String) extends RuntimeException

}
