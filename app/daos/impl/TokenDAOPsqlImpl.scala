package daos.impl

import daos.TokenDAO
import daos.convertors.TokenDOCConvertor.TokenRowToModel
import demo.Tables.TokenRow
import demo.Tables._
import models.Token
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class TokenDAOPsqlImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                (implicit ex: ExecutionContext) extends TokenDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val tokenQuery: Query[Token, TokenRow, Seq] = TableQuery[Token]

  protected def queryReturningToken = tokenQuery returning tokenQuery

  override def getById(tokenId: Long): Task[Option[Token]] =
    db.run(tokenQuery.filter(_.id === tokenId).result.headOption)
      .map(_.map(_.toModel)).wrapEx

  override def getAll: Task[Seq[Token]] = db
    .run(tokenQuery.result)
    .map(_.map(_.toModel()))
    .wrapEx

  override def create(token: Token): Task[Token] = db
    .run(queryReturningToken += token.).wrapEx

  override def update(token: Token): Task[Unit] = db.run(
    tokens.filter(_.id === token.id).update(token).checkRowsAffected
  ).wrapEx.map(_ => Unit)

  override def delete(id: Long): Task[Unit] = db.run(
    tokens.filter(_.id === id).delete.checkRowsAffected
  ).wrapEx.map(_ => Unit)

  override def getByTokenStringAndType(token: String, tokenType: String): Task[Option[Token]] =
    db.run(tokens.filter(t => t.token === token && t.tokenType === tokenType).result.headOption).wrapEx

  override def getByUserId(userId: Long): Task[Seq[Token]] =
    db.run(tokens.filter(_.userId === userId).result).wrapEx
}