package models.dtos.answers

import play.api.libs.json.{Json, OWrites}

import java.time.Instant

case class EmployeeDTO(id: Long,
                       organization_id:Long,
                       name: String,
                       info: String,
                       createdAt: Instant,
                       updatedAt: Instant)

object EmployeeDTO {
  implicit val writes: OWrites[EmployeeDTO] = Json.writes[EmployeeDTO]
}

