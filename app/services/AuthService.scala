package services


import models.dtos.SignInEmailAnswerDTO
import monix.eval.Task
import services.helpers.{BCryptHelper, Credentials, TimeHelper, UUIDGenerator}

trait AuthService {

  def signIn(credentials: Credentials)(implicit bcryptH: BCryptHelper): Task[SignInEmailAnswerDTO]

  def signUp(credentials: Credentials)(implicit bcryptH: BCryptHelper,
                                       uuidGenerator: UUIDGenerator,
                                       timeHelper: TimeHelper): Task[Unit]

}
