package models.dtos.question

import play.api.data.Form
import play.api.data.Forms._

case class SignUpForm(organization_id: Option[String],
                      email: String,
                      login: String,
                      password: String,
                      phone: String)

object SignUpForm {

  def validPassword(password: String): Boolean = {
    password.matches("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)[a-zA-Z\\d]{8,}$")
  }

  def validLogin(login: String): Boolean = {
    login.matches("^[a-z0-9_-]{3,16}$")
  }

  def validPhone(phone: String): Boolean = {
    phone.matches("^((\\+3|7|8)+([0-9]){10})$")
  }

  implicit val signUpForm: Form[SignUpForm] = Form(
    mapping(
      "organization_id"->optional(nonEmptyText),
      "email" -> email,
      "login" -> nonEmptyText.verifying(validLogin _),
      "password" -> nonEmptyText.verifying(validPassword _),
      "phone" -> nonEmptyText.verifying(validPhone _)
    )(SignUpForm.apply)(SignUpForm.unapply)
  )

}