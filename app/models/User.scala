package models

import models.dtos.UserDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class User(id: Long,
                email: String,
                login: String,
                password: String,
                phone: String,
               // image:String
                createdAt: Instant,
                updatedAt: Instant
               ) extends Model [Long,User] {

  def toDTO: UserDTO = UserDTO(id,login,password, email,phone, createdAt, updatedAt)
  override def updateModifiedField(): User = this.copy(updatedAt = Instant.now())
}

object User {
  implicit val format: OFormat[User] = Json.format[User]

  def tupled: ((Long, String, String, String, String, Instant, Instant)) => User = (User.apply _).tupled
}


