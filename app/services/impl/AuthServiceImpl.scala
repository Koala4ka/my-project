package services.impl

import daos.{TokenDAO, UserDAO}
import exceptions.Exceptions.WrongCredentials
import models.User
import models.dtos.{SignInEmailAnswerDTO, UserDTO}
import monix.eval.Task
import services.AuthService
import services.helpers.{BCryptHelper, Credentials, TimeHelper, UUIDGenerator}
import services.JWTService

import javax.inject.Inject

class AuthServiceImpl @Inject()(userDAO: UserDAO,
                                tokenDAO: TokenDAO,
                                jwtService: JWTService)
  extends AuthService {


  override def signIn(credentials: Credentials)
                     (implicit bcryptH: BCryptHelper): Task[SignInEmailAnswerDTO] = for {

    byEmail <- userDAO.getByEmail(credentials.email)
    validPassword <- byEmail match {
      case Some(user) => bcryptH.check(credentials.password, user.password)
      case _ => Task.now(false)
    }
    signInEmail <- (byEmail, validPassword) match {
      case (Some(user), true) =>
        val token = jwtService.createToken(user)
        tokenDAO
          .create(token)
          .map(_=> SignInEmailAnswerDTO(email = user.email, role = "admin", userId = user.id, token = token))

      case _ => throw WrongCredentials
    }

  } yield signInEmail

  override def signUp(credentials: Credentials)
                     (implicit bcryptH: BCryptHelper,
                      uuidGenerator: UUIDGenerator,
                      timeHelper: TimeHelper): Task[Unit] = ???
}



