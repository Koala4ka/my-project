package daos.generic

//class UserTable(tag: Tag, dbConfigProvider: DatabaseConfigProvider) extends Tables(dbConfigProvider)
//  with Table[User](tag, "users"){
//
//  def id: Rep[Long] = column[Long]("id", O.PrimaryKey, O.AutoInc)
//
//  def email: Rep[String] = column[String]("email")
//
//  def password: Rep[String] = column[String]("password")
//
//  def isEmailConfirmed: Rep[Boolean] = column[Boolean]("is_email_confirmed")
//
//  def createdAt: Rep[Instant] = column[Instant]("created_at")
//
//  def updatedAt: Rep[Instant] = column[Instant]("updated_at")
//
//  def * : ProvenShape[User] =
//    (id, email, password, isEmailConfirmed, createdAt, updatedAt) <> (User.tupled, User.unapply)
//}

