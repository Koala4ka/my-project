package models.dtos.question

import play.api.data.Form
import play.api.data.Forms._

case class SignUpForm(login: String, password: String,
                      email: String, phone: String)

object SignUpForm {

  def validPassword(password: String): Boolean = {
    password.matches("^(?=.*[0-9])(?=.*[a-z])(?=.*[A-Z])(?=.*[!@#&()–[{}]:;',?/*~$^+=<>]).{8,64}$")
  }

  def validLogin(login: String): Boolean = {
    login.matches("^[a-z0-9_-]{3,16}$")
  }

  def validPhone(phone:String):Boolean ={
    phone.matches("^((\\+3|7|8)+([0-9]){10})$")
  }

  implicit val signUpForm: Form[SignUpForm] = Form(
    mapping(
      "login" -> nonEmptyText.verifying(validLogin _), //validation login lke valid pass
      "email" -> email,
      "password" -> nonEmptyText.verifying(validPassword _),
      "phone" -> nonEmptyText.verifying(validPhone _)
    )(SignUpForm.apply)(SignUpForm.unapply)
  )

}