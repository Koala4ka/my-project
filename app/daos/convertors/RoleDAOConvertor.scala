package daos.convertors

import demo.Tables._
import models.Role

import java.sql.Timestamp

object RoleDAOConvertor {

  implicit class RoleDAOConvertor(roleRow: RolesRow) {

    def toModel(): Role = {
      Role(id = roleRow.id,
        name = roleRow.name,
        description = roleRow.description,
        createdAt = roleRow.createdAt.toInstant,
        updatedAt = roleRow.updatedAt.toInstant)
    }
  }
  implicit class RoleModelToRow(role: Role) {

        def toRow(): RolesRow = {
          RolesRow(id = role.id,
            name = role.name,
            description = role.description,
            createdAt = Timestamp.from(role.createdAt),
            updatedAt = Timestamp.from(role.updatedAt))
        }
      }
}


