package services


import models.User
import models.dtos.SignInEmailAnswerDTO
import models.dtos.question.{Credentials, SignUpForm}
import monix.eval.Task
import services.helpers.{BCryptHelper, TimeHelper, UUIDGenerator}

trait AuthService {

  def signIn(credentials: Credentials)(implicit bcryptH: BCryptHelper): Task[SignInEmailAnswerDTO]

  def signUp(signUp: SignUpForm)
            (implicit bcryptH: BCryptHelper,
             timeHelper: TimeHelper): Task[User]
}
