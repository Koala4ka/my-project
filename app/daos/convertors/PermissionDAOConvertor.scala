package daos.convertors

import demo.Tables._
import models.{Permission}

import java.sql.Timestamp

object PermissionDAOConvertor {

  implicit class PermissionDAOConvertor(permissionRow: PermissionTableRow) {

    def toModel(): Permission = {
      Permission(id = permissionRow.id,
        name = permissionRow.name,
        description = permissionRow.description,
        createdAt = permissionRow.createdAt.toInstant,
        updatedAt = permissionRow.updatedAt.toInstant)
    }
  }
  implicit class PermissionModelToRow(permission: Permission) {

        def toRow(): PermissionTableRow = {
          PermissionTableRow(id = permission.id,
            name = permission.name,
            description = permission.description,
            createdAt = Timestamp.from(permission.createdAt),
            updatedAt = Timestamp.from(permission.updatedAt))
        }
      }
}


