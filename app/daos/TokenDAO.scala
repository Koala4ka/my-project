package daos

import models.Token
import monix.eval.Task

trait TokenDAO extends ModelDAO[Token, Long] {

  def getByUserId(userId: Long): Task[Seq[Token]]
}
