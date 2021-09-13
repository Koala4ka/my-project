package daos.impl

import daos.TokenDAO
import daos.convertors.TokenDAOConvertor.TokenRowToModel
import demo.Tables._
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import daos.convertors.TokenDAOConvertor._

import javax.inject.Inject
import scala.concurrent.ExecutionContext


class TokenDAOPsqlImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                (implicit ex: ExecutionContext) extends TokenDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val tokenQuery: Query[AuthToken, AuthTokenRow, Seq] = TableQuery[AuthToken]

  protected def queryReturningToken = tokenQuery returning tokenQuery

  import models.Token

  override def getById(tokenId: Long): Task[Option[Token]] =
    db.run(tokenQuery.filter(_.id === tokenId).result.headOption)
      .map(_.map(_.toModel)).wrapEx

  override def getAll: Task[Seq[Token]] = db.run(tokenQuery.result)
    .map(_.map(_.toModel))
    .wrapEx

  override def create(token: Token): Task[Token] =
    db
      .run(queryReturningToken += token.toRow)
      .wrapEx
      .map(_.toModel)


  def update(token: Token)
  : Task[Token] = {
    if (token.id == 0)
      throw new RuntimeException()
    val tokenId = token.id
    val updateAction = tokenQuery.filter(_.id === tokenId).update(token.updateModifiedField().toRow)
      .map { rowsUpdated =>
        token.updateModifiedField()
        if (rowsUpdated == 1)
          token.updateModifiedField()
        else throw new RuntimeException()
      }
    db.run(updateAction).wrapEx
  }

  override def delete(id: Long): Task[Unit] = db.run(
    tokenQuery.filter(_.id === id).delete
  ).wrapEx.map(_ => ())


  override def getByUserId(userId: Long): Task[Seq[Token]] =
    db.run(tokenQuery.filter(_.userId === userId).result)
      .map(_.map(_.toModel))
      .wrapEx
}
