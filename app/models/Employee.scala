package models

import models.dtos.answers.EmployeeDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Employee(id: Long,
                    organization_id: Long,
                    name: String,
                    info: String,
                    createdAt: Instant,
                    updatedAt: Instant) extends Model[Long, Employee] {

  def toDTO: EmployeeDTO = EmployeeDTO(id, organization_id, name, info, createdAt, updatedAt)

  override def updateModifiedField(): Employee = this.copy(updatedAt = Instant.now())

}

object Employee {
  implicit val format: OFormat[Employee] = Json.format[Employee]

  def tupled: ((Long, Long, String, String, Instant, Instant)) => Employee = (Employee.apply _).tupled
}