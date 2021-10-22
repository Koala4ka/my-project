package models.dtos.answers

import models.Token
import play.api.libs.json.{Json, OWrites}

case class SignInEmailAnswerDTO(email:String,
                                role: String,
                                userId: Long,
                                token:Token)


object SignInEmailAnswerDTO {
  implicit val writes: OWrites[SignInEmailAnswerDTO] = Json.writes[SignInEmailAnswerDTO]

}
