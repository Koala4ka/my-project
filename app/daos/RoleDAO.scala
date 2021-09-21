package daos

import models.{Role}
import monix.eval.Task

trait RoleDAO extends ModelDAO[Role, Long] {

  def getByUserId(userId: Long): Task[Seq[Role]]

}
