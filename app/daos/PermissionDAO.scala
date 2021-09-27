package daos

import models.Permission
import monix.eval.Task

trait PermissionDAO extends ModelDAO[Permission, Long] {

  def getByRoleId(roleId: Long): Task[Seq[Permission]]

}
