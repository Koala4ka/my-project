package daos

import models.User
import monix.eval.Task

trait UserDAO extends ModelDAO[User, Long] {

  def getByEmail(email: String): Task[Option[User]]

  def validateUser(email: String, login: String, phone: String): Task[Boolean]

  def getByOrgId(orgId:Long):Task[Seq[User]]
}
