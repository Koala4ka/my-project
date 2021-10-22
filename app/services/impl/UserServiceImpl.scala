package services.impl

import daos.UserDAO
import monix.eval.Task
import services.UserService
import exceptions.Exceptions.{NotFoundException, UserDosNotExist}
import models.User
import models.dtos.answers.UserDTO
import models.dtos.question.UserUpdateQuestion
import services.helpers.BCryptHelper

import java.time.Instant
import javax.inject.Inject


class UserServiceImpl @Inject()(userDAO: UserDAO) extends UserService {

  override def getUser(userId: Long): Task[UserDTO] = userDAO.getById(userId).map{
    case Some(user) => user.toDTO
    case _ => throw NotFoundException("user", s"id=$userId")
  }

  override def getAll(orgId:Option[Long]): Task[Seq[User]] = orgId match {
    case Some(organizationId) => userDAO.getByOrgId(organizationId)
    case None => userDAO.getAll
  }

  override def update(userUpdateQuestion: UserUpdateQuestion)
                     (implicit bcryptH: BCryptHelper): Task[UserDTO] = for{
   user<- userDAO.getById(userUpdateQuestion.id.toLong).map(_.getOrElse(
     throw UserDosNotExist
   ))
    updateUser <-userDAO.update(User
    (id = user.id,
      organization_id=user.organization_id,
      email = userUpdateQuestion.email.getOrElse(user.email),
      login = userUpdateQuestion.login.getOrElse(user.login),
      password = bcryptH.bcrypt(userUpdateQuestion.password.getOrElse(user.password)),
      phone = userUpdateQuestion.phone.getOrElse(user.phone),
      createdAt = user.createdAt,
      updatedAt = Instant.now))
  }yield updateUser.toDTO

}
