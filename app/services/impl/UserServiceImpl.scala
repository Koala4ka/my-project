package services.impl

import daos.UserDAO
import models.dtos.UserDTO
import monix.eval.Task
import services.UserService
import exceptions.Exceptions.NotFoundException
import models.User
import services.helpers.TimeHelper

import javax.inject.Inject

class UserServiceImpl @Inject()(userDAO: UserDAO) extends UserService {

  override def getUser(userId: Long): Task[UserDTO] = userDAO.getById(userId).map{
    case Some(user) => user.toDTO
    case _ => throw NotFoundException("user", s"id=$userId")
  }

  override def getAll(): Task[Seq[User]] = userDAO.getAll

//  override def update(user: User): Task[UserDTO] =
//    userDAO.update(user)
}
