package services

import models.User
import models.dtos.UserDTO
import monix.eval.Task
import services.helpers.TimeHelper

trait UserService {

  def update(user: User): Task[UserDTO]

  def getAll(): Task[Seq[User]]

  def getUser(userId: Long): Task[UserDTO]
}
