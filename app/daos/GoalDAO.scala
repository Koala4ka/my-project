package daos

import models.{Goal}
import monix.eval.Task

trait GoalDAO extends ModelDAO[Goal, Long] {

  def getByEmployeeId(employeeId: Long): Task[Seq[Goal]]

}



