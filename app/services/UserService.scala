package services

import models.User
import models.dtos.UserDTO
import models.dtos.question.UserUpdateQuestion
import monix.eval.Task

trait UserService {

  def update(userUpdateQuestion: UserUpdateQuestion): Task[UserDTO]

  def getAll(): Task[Seq[User]]

  def getUser(userId: Long): Task[UserDTO]
}
