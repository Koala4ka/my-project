package daos

import models.Employee
import monix.eval.Task

trait EmployeeDAO extends ModelDAO[Employee, Long] {

  def getByOrgId(orgId: Long): Task[Seq[Employee]]

}
