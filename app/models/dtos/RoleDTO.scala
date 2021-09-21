package models.dtos

import play.api.libs.json.{Json, OWrites}

import java.time.Instant


case class RoleDTO(id: Long,
                   name: String,
                   description: String,
                   createdAt: Instant,
                   updatedAt: Instant)

object RoleDTO {
  implicit val writes: OWrites[RoleDTO] = Json.writes[RoleDTO]

}
