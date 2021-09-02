package daos.convertors

import demo.Tables._
import models.User

import java.sql.Timestamp

object UserDAOConvertor {

  implicit class UserRowToModel(usersRow: UsersRow) {

    def toModel(): User = {
      User(id = usersRow.id,
        login = usersRow.login,
        password = usersRow.password,
        email = usersRow.email,
        phone = usersRow.phone,
        createdAt = usersRow.createdAt.toInstant,
        updatedAt = usersRow.updatedAt.toInstant
      )
    }
  }

  implicit class UserModelToRow(user: User) {

    def toRow(): UsersRow = {
      UsersRow(id = user.id,
        login = user.login,
        password = user.password,
        email = user.email,
        phone = user.email,
        createdAt = Timestamp.from(user.createdAt),
        updatedAt = Timestamp.from(user.updatedAt))
    }
  }
}
