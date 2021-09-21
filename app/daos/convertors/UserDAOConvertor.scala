package daos.convertors

import demo.Tables._
import models.User

import java.sql.Timestamp

object UserDAOConvertor {

  implicit class UserRowToModel(usersRow: UsersRow) {

    def toModel(): User = {
      User(id = usersRow.id,
        email = usersRow.email,
        login = usersRow.login,
        password = usersRow.password,
        phone = usersRow.phone,
        createdAt = usersRow.createdAt.toInstant,
        updatedAt = usersRow.updatedAt.toInstant
      )
    }
  }

  implicit class UserModelToRow(user: User) {

    def toRow(): UsersRow = {
      UsersRow(id = user.id,
        email = user.email,
        login = user.login,
        password = user.password,
        phone = user.phone,
        createdAt = Timestamp.from(user.createdAt),
        updatedAt = Timestamp.from(user.updatedAt))
    }
  }
}
