package daos.convertors

import demo.Tables._
import models.Goal

import java.sql.Timestamp

object GoalConvertor {

  implicit class GoalRowToModel(goalRow: GoalTableRow) {

    def toModel(): Goal = {
      Goal(id = goalRow.id,
        employeeId = goalRow.employeeId,
        name = goalRow.name,
        info = goalRow.info,
        createdAt = goalRow.createdAt.toInstant,
        updatedAt = goalRow.updatedAt.toInstant)
    }
  }

  implicit class GoalModelToRow(goal: Goal) {

    def toRow(): GoalTableRow = {
      GoalTableRow(id = goal.id,
        employeeId = goal.employeeId,
        name = goal.name,
        info = goal.info,
        createdAt = Timestamp.from(goal.createdAt),
        updatedAt = Timestamp.from(goal.updatedAt))
    }
  }
}
