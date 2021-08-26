package models

import models.dtos.UserDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class User(id: Long,
                login: String,
                password: String,
                email: String,
                phone: String,
               // image:String
                createdAt: Instant,
                updatedAt: Instant
               ) extends HasId[Long] {
  def toDTO: UserDTO = UserDTO(id,login,password, email,phone, createdAt, updatedAt)
}

object User {
  implicit val format: OFormat[User] = Json.format[User]

  def tupled: ((Long, String, String, String, String, Instant, Instant)) => User = (User.apply _).tupled
}
