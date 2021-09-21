package services.impl

import daos.{TokenDAO, UserDAO}
import exceptions.Exceptions.{UserAlreadyExistsException, WrongCredentials}
import models.User
import models.dtos.question.{Credentials, SignUpForm}
import models.dtos.{SignInEmailAnswerDTO, UserDTO}
import monix.eval.Task
import services.AuthService
import services.helpers.{BCryptHelper, TimeHelper, UUIDGenerator}
import services.JWTService

import java.time.Instant
import javax.inject.Inject

class AuthServiceImpl @Inject()(userDAO: UserDAO,
                                tokenDAO: TokenDAO,
                                jwtService: JWTService)
  extends AuthService {


  override def signIn(credentials: Credentials)
                     (implicit bcryptH: BCryptHelper): Task[SignInEmailAnswerDTO] = for {
    byEmail <- userDAO.getByEmail(credentials.email)
    validPassword = byEmail match {
      case Some(user) =>
        bcryptH.check(credentials.password, user.password)
      case _ => Task.now(false)
    }

    signInEmail <- (byEmail, validPassword) match {
      case (Some(user), true) =>
        val token = jwtService.createToken(user)
        tokenDAO
          .create(token)
          .map(_ => {
            println("token" + token)
            SignInEmailAnswerDTO(email = user.email, role = "admin", userId = user.id, token = token)
          })
      case _ => throw WrongCredentials
    }
  } yield signInEmail

  override def signUp(signUp: SignUpForm)
                     (implicit bcryptH: BCryptHelper,
                      timeHelper: TimeHelper): Task[User] = for {
    validateUser <- userDAO.validateUser(signUp.email, signUp.login, signUp.phone)
    createUser <- validateUser match {
      case false => userDAO.create(User(id = 0, email = signUp.email,
        login = signUp.login,
        password =  bcryptH.bcrypt(signUp.password),
        phone = signUp.phone,
        createdAt = Instant.now(),
        updatedAt = Instant.now()))
      case true => throw UserAlreadyExistsException
    }
  } yield createUser
}

