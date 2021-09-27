package models.dtos

import play.api.libs.json.{Json, OWrites}

import java.time.Instant


case class PermissionDTO(id: Long,
                   name: String,
                   description: String,
                   createdAt: Instant,
                   updatedAt: Instant)

object PermissionDTO {
  implicit val writes: OWrites[PermissionDTO] = Json.writes[PermissionDTO]

}