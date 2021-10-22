package daos.convertors

import demo.Tables._
import models.{Employee}

import java.sql.Timestamp

object EmployeeConvertor {

  implicit class EmployeeRowToModel(employeeRow: EmployeeTableRow) {

    def toModel(): Employee = {
      Employee(id = employeeRow.id,
        organization_id = employeeRow.organizationId,
        name = employeeRow.name,
        info = employeeRow.info,
        createdAt = employeeRow.createdAt.toInstant,
        updatedAt = employeeRow.updatedAt.toInstant)
    }
  }

  implicit class EmployeeModelToRow(employee: Employee) {

    def toRow(): EmployeeTableRow = {
      EmployeeTableRow(id = employee.id,
        organizationId = employee.organization_id,
        name = employee.name,
        info = employee.info,
        createdAt = Timestamp.from(employee.createdAt),
        updatedAt = Timestamp.from(employee.updatedAt))
    }
  }
}
