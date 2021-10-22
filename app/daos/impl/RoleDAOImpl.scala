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


  override def getAll: Task[Seq[Role]] =
    db
      .run(roleQuery.result)
      .map(_.map(_.toModel()))
      .wrapEx

  override def getById(roleId: Long): Task[Option[Role]] =
    db
      .run(
        roleQuery.filter(_.id === roleId).result.headOption
      ).map(_.map(_.toModel())).wrapEx

  override def create(role: Role): Task[Role] =
    db
      .run(queryReturningRole += role.toRow())
      .wrapEx
      .map(_.toModel())

    def update(role: Role): Task[Role] = {
      if (role.id == 0)
        throw new RuntimeException()
      val roleId = role.id
      val updateAction = roleQuery.filter(_.id === roleId)
        .update(role.updateModifiedField().toRow)
        .map { rowsUpdated =>
          role.updateModifiedField()
          if (rowsUpdated == 1)
            role.updateModifiedField()
          else throw new RuntimeException()
        }
      db.run(updateAction).wrapEx
    }

  override def delete(id: Long): Task[Unit] =
    db
      .run(
        roleQuery.filter(_.id === id).delete
      ).wrapEx.map(_ => ())
}


