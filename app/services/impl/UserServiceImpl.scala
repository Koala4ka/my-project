package services.impl

import daos.UserDAO
import models.dtos.UserDTO
import monix.eval.Task
import services.UserService
import exceptions.Exceptions.{NotFoundException, UserDosNotExist}
import models.User
import models.dtos.question.UserUpdateQuestion
import java.time.Instant
import javax.inject.Inject


class UserServiceImpl @Inject()(userDAO: UserDAO) extends UserService {

  override def getUser(userId: Long): Task[UserDTO] = userDAO.getById(userId).map{
    case Some(user) => user.toDTO
    case _ => throw NotFoundException("user", s"id=$userId")
  }

  override def getAll(): Task[Seq[User]] = userDAO.getAll

  override def update(userUpdateQuestion: UserUpdateQuestion): Task[UserDTO] = for{
   user<- userDAO.getById(userUpdateQuestion.id.toLong).map(_.getOrElse(
     throw UserDosNotExist
   ))
    updateUser <-userDAO.update(User
    (id = user.id,
      email = user.email,
      login = user.login,
      password = user.password,
      phone = user.phone,
      createdAt = user.createdAt,
      updatedAt = Instant.now))
  }yield updateUser.toDTO

}
