package models.dtos.question

import play.api.data.Form
import play.api.data.Forms._

case class CreateGoalForm(employeeId: String,
                          name: String,
                          info: String)

object CreateGoalForm {

  implicit val createGoalForm: Form[CreateGoalForm] = Form(
    mapping(
      "employeeId" -> nonEmptyText,
      "name" -> nonEmptyText,
      "info" -> nonEmptyText
    )(CreateGoalForm.apply)(CreateGoalForm.unapply)
  )

}
