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


class UserDAOPsqlImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ex: ExecutionContext) extends UserDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val usersQuery: Query[Users, UsersRow, Seq] = TableQuery[Users]

  override def getById(userId: Long): Task[Option[User]] =
    db.run(usersQuery.filter(_.id === userId).result.headOption)
      .map(_.map(_.toModel)).wrapEx

  override def getAll: Task[Seq[User]] = db.run(usersQuery.result)
    .map(_.map(_.toModel))
    .wrapEx

  override def create(user: User): Task[User] = db.run(usersQuery += user.)
    .map(_ => user)
    .wrapEx

  protected def queryReturningId: profile.ReturningInsertActionComposer[UsersRow, Long] =
    usersQuery returning usersQuery += usersQuery

//  override def save(user: User): Task[User] = db.run {
//    users returning users += user

  override def update(user: User): Task[Unit] = db.run(
    usersQuery.filter(_.id === user.id).update(user.).checkRowsAffected
  ).wrapEx.map(_ => Unit)



  override def delete(id: Long): Task[Unit] = db.run(
    usersQuery.filter(_.id === id).delete.checkRowsAffected
  ).wrapEx.map(_ => Unit)

  override def getByEmail(email: String): Task[Option[User]] =
    db.run(usersQuery.filter(_.email === email).result.headOption).wrapEx

 // protected def queryReturningId: profile.ReturningInsertActionComposer[UsersRow, Long] = query returning query.map(_.id)
}
