package daos.impl

import demo.Tables._
import daos.GoalDAO
import demo.Tables.GoalTableRow
import models.Goal
import monix.eval.Task
import play.api.db.slick.{DatabaseConfigProvider, HasDatabaseConfigProvider}
import slick.jdbc.JdbcProfile
import daos.convertors.GoalConvertor._

import javax.inject.Inject
import scala.concurrent.ExecutionContext

class GoalDAOImpl @Inject()(protected val dbConfigProvider: DatabaseConfigProvider)
                           (implicit ex: ExecutionContext) extends GoalDAO

  with HasDatabaseConfigProvider[JdbcProfile] {

  import daos.helpers.Helpers._
  import profile.api._

  private val goalQuery: Query[GoalTable, GoalTableRow, Seq] = TableQuery[GoalTable]

  protected def queryReturningGoal = goalQuery returning goalQuery

  override def getByEmployeeId(employeeId: Long): Task[Seq[Goal]] =
    db
      .run(goalQuery.filter(_.employeeId === employeeId).result)
      .map(_.map(_.toModel()))
      .wrapEx

  override def getAll: Task[Seq[Goal]] =
    db
      .run(goalQuery.result)
      .map(_.map(_.toModel()))
      .wrapEx

  override def getById(goalId: Long): Task[Option[Goal]] =
    db
      .run(goalQuery.filter(_.id === goalId).result.headOption)
      .map(_.map(_.toModel()))
      .wrapEx

  override def create(goal: Goal): Task[Goal] =
    db
      .run(queryReturningGoal += goal.toRow())
      .wrapEx
      .map(_.toModel())

  override def update(goal: Goal): Task[Goal] = ???

  override def delete(id: Long): Task[Unit] =
    db
      .run(goalQuery.filter(_.id === id).delete)
      .wrapEx
      .map(_ => ())
}


//  def update(role: Role): Task[Role] = {
//    if (role.id == 0)
//      throw new RuntimeException()
//    val roleId = role.id
//    val updateAction = roleQuery.filter(_.id === roleId)
//      .update(role.updateModifiedField().toRow)
//      .map { rowsUpdated =>
//        role.updateModifiedField()
//        if (rowsUpdated == 1)
//          role.updateModifiedField()
//        else throw new RuntimeException()
//      }
//    db.run(updateAction).wrapEx
//  }
//
