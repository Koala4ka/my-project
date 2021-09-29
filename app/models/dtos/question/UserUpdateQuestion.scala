package models.dtos.question

import play.api.data.Form
import play.api.data.Forms.{email, mapping, nonEmptyText, optional}

case class UserUpdateQuestion(id: String,
                              email: Option[String],
                              login: Option[String],
                              password: Option[String],
                              phone: Option[String])

object UserUpdateQuestion {

  def validPassword(password: String): Boolean = {
    password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
  }

  def validLogin(login: String): Boolean = {
    login.matches("^[a-z0-9_-]{3,16}$")
  }

  def validPhone(phone:String):Boolean ={
    phone.matches("^((\\+3|7|8)+([0-9]){10})$")
  }


  implicit val userUpdateQuestion: Form[UserUpdateQuestion] = Form(
    mapping(
      "id"-> nonEmptyText,
      "email" -> optional(email),
      "login"-> optional(nonEmptyText),
      "password" -> optional(nonEmptyText),
      "phone" -> optional(nonEmptyText)
    )(UserUpdateQuestion.apply)(UserUpdateQuestion.unapply)
  )

}
