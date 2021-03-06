package models.dtos.answers

import play.api.libs.json.{Json, OWrites}

import java.time.Instant

case class UserDTO(id: Long,
                   organization_id: Option[Long],
                   email: String,
                   login: String,
                   password: String,
                   phone: String,
                   createdAt: Instant,
                   updatedAt: Instant)

object UserDTO {

  implicit val writes: OWrites[UserDTO] = Json.writes[UserDTO]

}
