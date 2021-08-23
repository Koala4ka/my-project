package daos.generic

import models.User
import play.api.db.slick.DatabaseConfigProvider
import slick.jdbc.JdbcProfile
import slick.lifted
import slick.lifted.ProvenShape
import exceptions.Exceptions.DbResultException

import java.time.Instant
import scala.concurrent.ExecutionContext


class Tables(dbConfigProvider: DatabaseConfigProvider) {
  val conf = dbConfigProvider.get[JdbcProfile]

  import conf.profile.api._

  implicit class SafeDB(action: conf.profile.ProfileAction[Int, NoStream, Effect.Write]) {
    def checkRowsAffected(implicit ex: ExecutionContext):
    DBIOAction[Int, NoStream, Effect.Write with Effect with Effect.Transactional] =
      action.flatMap { updatedRows =>
        if (updatedRows == 0) DBIO.failed(DbResultException)
        else DBIO.successful(updatedRows)
      }.transactionally
  }

  class UserTable(tag: Tag) extends Table[User](tag, "users") {
    def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)

    def login: Rep[String] = column[String]("login")

    def password: Rep[String] = column[String]("password")

    def email: Rep[String] = column[String]("email")

    def phone: Rep[Int] = column[Int]("phone")

    def createdAt: Rep[Instant] = column[Instant]("created_at")

    def updatedAt: Rep[Instant] = column[Instant]("updated_at")

    def * : ProvenShape[User] =
      (id, login, email, password, phone, createdAt, updatedAt) <> (User.tupled, User.unapply)
  }

  val users = lifted.TableQuery[UserTable]

}
