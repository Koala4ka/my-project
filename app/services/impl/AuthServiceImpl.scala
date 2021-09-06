package services.impl

import daos.{TokenDAO, UserDAO}
import exceptions.Exceptions.WrongCredentials
import models.User
import models.dtos.UserDTO
import monix.eval.Task
import services.AuthService
import services.helpers.{BCryptHelper, Credentials, TimeHelper, UUIDGenerator}
import utils.TokenUtils

import javax.inject.Inject

class AuthServiceImpl @Inject()(userDAO: UserDAO,
                                tokenDAO: TokenDAO) extends AuthService {

  override def signIn(credentials: Credentials)
                     (implicit bcryptH: BCryptHelper): Task[User] = for {
    byEmail <- userDAO.getByEmail(credentials.email)
    validPassword <- byEmail match {
      case Some(user) => bcryptH.check(credentials.password, user.password)
      case _ => Task.now(false)
    }
    userDTO = (byEmail, validPassword) match {
      case (Some(user), true) => user
      case _ => throw WrongCredentials
    }

  } yield userDTO

  override def signUp(credentials: Credentials)
                     (implicit bcryptH: BCryptHelper,
                      uuidGenerator: UUIDGenerator,
                      timeHelper: TimeHelper): Task[Unit] = ???
}



