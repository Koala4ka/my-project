package daos.impl

import daos.RoleDAO
import demo.Tables.RolesRow
import models.Role
import demo.Tables._
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import daos.convertors.RoleDAOConvertor._

import javax.inject.Inject
import scala.concurrent.ExecutionContext


class RoleDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ex: ExecutionContext) extends RoleDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val roleQuery: Query[Roles, RolesRow, Seq] = TableQuery[Roles]


  private val usersRoleQuery: Query[RoleUser, RoleUserRow, Seq] = TableQuery[RoleUser]

  protected def queryReturningRole = roleQuery returning roleQuery

  override def getByUserId(userId: Long): Task[Option[Role]] =
    db
      .run(usersRoleQuery.filter(_.userId === userId)
        .join(roleQuery)
        .on(_.roleId === _.id)
        .map(_._2)
        .result.headOption)
      .map(_.map(_.toModel))
      .wrapEx


  override def getAll: Task[Seq[Role]] = ???

  override def getById(id: Long): Task[Option[Role]] = ???

  override def create(model: Role): Task[Role] = ???

  override def update(model: Role): Task[Role] = ???

  override def delete(Id: Long): Task[Unit] = ???
}




//  override def getById(userId: Long): Task[Option[User]] =
//    db.run(usersQuery.filter(_.id === userId).result.headOption)
//      .map(_.map(_.toModel)).wrapEx
//
//  override def getAll: Task[Seq[User]] = db.run(usersQuery.result)
//    .map(_.map(_.toModel))
//    .wrapEx
//
//  override def create(user: User): Task[User] =
//    db
//      .run(queryReturningUser += user.toRow)
//      .wrapEx
//      .map(_.toModel)
//
//
//  def update(user: User)
//  : Task[User] = {
//    if (user.id == 0)
//      throw new RuntimeException()
//    val userId = user.id
//    val updateAction = usersQuery.filter(_.id === userId)
//      .update(user.updateModifiedField().toRow)
//      .map { rowsUpdated =>
//        user.updateModifiedField()
//        if (rowsUpdated == 1)
//          user.updateModifiedField()
//        else throw new RuntimeException()
//      }
//    db.run(updateAction).wrapEx
//  }
//
//  override def delete(id: Long): Task[Unit] = db.run(
//    usersQuery.filter(_.id === id).delete
//  ).wrapEx.map(_ => ())
//
//  override def getByEmail(email: String): Task[Option[User]] =
//    db.run(usersQuery.filter(_.email === email).result.headOption)
//      .wrapEx
//      .map(_.map(_.toModel))
//
//  override def validateUser(email: String, login: String, phone: String): Task[Boolean] =
//    db.run(usersQuery.filter(user => user.login === login &&
//      user.email === email &&
//      user.phone === phone)
//      .result
//      .headOption)
//      .wrapEx
//      .map(_.isDefined)
//
//}
