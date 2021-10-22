package services

import models.User
import models.dtos.answers.UserDTO
import models.dtos.question.UserUpdateQuestion
import monix.eval.Task
import services.helpers.BCryptHelper

trait UserService {

  def update(userUpdateQuestion: UserUpdateQuestion)(implicit bcryptH: BCryptHelper): Task[UserDTO]

  def getAll(orgId:Option[Long]): Task[Seq[User]]

  def getUser(userId: Long): Task[UserDTO]
}
