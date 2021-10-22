package daos.impl

import daos.EmployeeDAO
import models.Employee
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import demo.Tables._
import daos.convertors.EmployeeConvertor._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class EmployeeDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                               (implicit ex: ExecutionContext) extends EmployeeDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val employeeQuery: Query[EmployeeTable, EmployeeTableRow, Seq] = TableQuery[EmployeeTable]

  override def getByOrgId(orgId: Long): Task[Seq[Employee]] =
    db
      .run(employeeQuery.filter(_.organizationId === orgId).result)
      .map(_.map(_.toModel()))
      .wrapEx

  override def getAll: Task[Seq[Employee]] =
    db
      .run(employeeQuery.result)
      .map(_.map(_.toModel()))
      .wrapEx

  override def getById(id: Long): Task[Option[Employee]] = ???

  override def create(model: Employee): Task[Employee] = ???

  override def update(model: Employee): Task[Employee] = ???

  override def delete(id: Long): Task[Unit] = ???


}
