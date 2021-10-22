package daos.impl

import daos.UserDAO
import demo.Tables._
import models.User
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import daos.convertors.UserDAOConvertor._

import javax.inject.Inject
import scala.concurrent.ExecutionContext


class UserDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ex: ExecutionContext) extends UserDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val usersQuery: Query[Users, UsersRow, Seq] = TableQuery[Users]

  protected def queryReturningUser = usersQuery returning usersQuery

  override def getById(userId: Long): Task[Option[User]] =
    db.run(usersQuery.filter(_.id === userId).result.headOption)
      .map(_.map(_.toModel())).wrapEx

  override def getAll: Task[Seq[User]] =
    db
      .run(usersQuery.result)
      .map(_.map(_.toModel()))
      .wrapEx

  override def create(user: User): Task[User] =
    db
      .run(queryReturningUser += user.toRow())
      .wrapEx
      .map(_.toModel())


  def update(user: User)
  : Task[User] = {
    if (user.id == 0)
      throw new RuntimeException()
    val userId = user.id
    val updateAction = usersQuery.filter(_.id === userId)
      .update(user.updateModifiedField().toRow())
      .map { rowsUpdated =>
        user.updateModifiedField()
        if (rowsUpdated == 1)
          user.updateModifiedField()
        else throw new RuntimeException()
      }
    db.run(updateAction).wrapEx
  }

  override def delete(id: Long): Task[Unit] = db.run(
    usersQuery.filter(_.id === id).delete
  ).wrapEx.map(_ => ())

  override def getByEmail(email: String): Task[Option[User]] =
    db.run(usersQuery.filter(_.email === email).result.headOption)
      .wrapEx
      .map(_.map(_.toModel()))

  override def validateUser(email: String, login: String, phone: String): Task[Boolean] =
    db.run(usersQuery.filter(user => user.login === login &&
      user.email === email &&
      user.phone === phone)
      .result
      .headOption)
      .wrapEx
      .map(_.isDefined)

  override def getByOrgId(orgId: Long): Task[Seq[User]] =
    db.
      run(usersQuery.filter(user => user.organizationId === orgId)
        .result)
      .wrapEx
      .map(_.map(_.toModel()))

}
