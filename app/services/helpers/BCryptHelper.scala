package services.helpers

import monix.eval.Task
import com.github.t3hnar.bcrypt._

trait BCryptHelper {
  def bcrypt(password: String, rounds: Int = BCryptHelper.Rounds): String
  def check(plainPassword: String, hash: String):  Boolean
}

object BCryptHelper {
  val Rounds = 15

  implicit val defaultHelper: BCryptHelper = new BCryptHelper {
    override def bcrypt(password: String, rounds: Int): String =  {
      password.bcryptBounded(rounds)
    }

    override def check(plainPassword: String, hash: String): Boolean =  {
      plainPassword.isBcryptedBounded(hash)
    }
  }
}
