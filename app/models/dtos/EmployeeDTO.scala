package models.dtos

import play.api.libs.json.{Json, OWrites}

import java.time.Instant

case class EmployeeDTO(id: Long,
                       name: String,
                       sailingGoal: String,
                       info: String,
                       createdAt: Instant,
                       updatedAt: Instant)

object EmployeeDTO {
  implicit val writes: OWrites[EmployeeDTO] = Json.writes[EmployeeDTO]
}

