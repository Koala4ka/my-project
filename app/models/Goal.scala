package models

import models.dtos.answers.GoalDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Goal(id: Long,
                employeeId: Long,
                name:String,
                info:String,
                createdAt: Instant,
                updatedAt: Instant){

  def toDTO: GoalDTO = GoalDTO(id, employeeId, name,info, createdAt, updatedAt)
}

object Goal {

  implicit val format: OFormat[Goal] = Json.format[Goal]

  def tupled: ((Long,Long, String, String,Instant,Instant)) => Goal = (Goal.apply _).tupled
}