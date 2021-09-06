package daos.convertors

import demo.Tables._
import models.Token

import java.sql.Timestamp

object TokenDAOConvertor {

  implicit class TokenRowToModel(tokenRow: TokenRow) {

    def toModel(): Token = {
      Token(id = tokenRow.id,
        userId = tokenRow.userId,
        token = tokenRow.token,
        createdAt = tokenRow.createdAt.toInstant)
    }
  }

  implicit class TokenModelToRow(token: Token) {

    def toRow(): TokenRow = {
      TokenRow(id = token.id,
        userId = token.userId,
        token = token.token,
        createdAt = Timestamp.from(token.createdAt))
    }
  }
}
