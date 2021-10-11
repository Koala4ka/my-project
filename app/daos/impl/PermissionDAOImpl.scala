package daos.impl

import daos.PermissionDAO
import models.Permission
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import demo.Tables._
import daos.convertors.PermissionDAOConvertor._

import javax.inject.Inject
import scala.concurrent.ExecutionContext


class PermissionDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                                 (implicit ex: ExecutionContext) extends PermissionDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val permissionRoleQuery: Query[PermissionRole, PermissionRoleRow, Seq] = TableQuery[PermissionRole]

  private val permissionQuery: Query[PermissionTable, PermissionTableRow, Seq] = TableQuery[PermissionTable]

  protected def queryReturningPermission = permissionQuery returning permissionQuery

  override def getByRoleId(roleId: Long): Task[Seq[Permission]] = db
    .run(permissionRoleQuery.filter(_.roleId === roleId)
      .join(permissionQuery)
      .on(_.permissionId === _.id)
      .map(_._2)
      .result)
    .map(_.map(_.toModel))
    .wrapEx


  override def getAll: Task[Seq[Permission]] =
    db
      .run(permissionQuery.result)
      .map(_.map(_.toModel))
      .wrapEx

  override def create(permission: Permission): Task[Permission] =
    db
      .run(queryReturningPermission += permission.toRow)
      .wrapEx
      .map(_.toModel)

  override def update(permission: Permission): Task[Permission] = ???

  override def delete(Id: Long): Task[Unit] = ???

  override def getById(id: Long): Task[Option[Permission]] = ???
}
