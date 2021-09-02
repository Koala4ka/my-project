package services

import models.dtos.UserDTO
import monix.eval.Task

trait UserService {
  def createUser
  def getUser(userId: Long): Task[UserDTO]
}
