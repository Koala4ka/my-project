package models

import models.dtos.EmployeeDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Employee(id: Long,
                    name: String,
                    sailingGoal: String,
                    info: String,
                    createdAt: Instant,
                    updatedAt: Instant) extends Model[Long, Employee] {

  def toDTO: EmployeeDTO = EmployeeDTO(id, name, sailingGoal, info, createdAt, updatedAt)

  override def updateModifiedField(): Employee = this.copy(updatedAt = Instant.now())

}

object Employee {
  implicit val format: OFormat[Employee] = Json.format[Employee]

  def tupled: ((Long, String, String, String, Instant, Instant)) => Employee = (Employee.apply _).tupled
}