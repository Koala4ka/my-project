package models.dtos.answers

import play.api.libs.json.{Json, OWrites}

import java.time.Instant

case class GoalDTO(id: Long,
                   employee_id: Long,
                   name: String,
                   info: String,
                   createdAt: Instant,
                   updatedAt: Instant)

object GoalDTO {

  implicit val writes: OWrites[GoalDTO] = Json.writes[GoalDTO]

}

