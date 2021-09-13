package models


import play.api.libs.json.{Json, OFormat, Writes}

import java.time.Instant


case class Token(id: Long,
                 userId: Long,
                 token: String,
                 createdAt: Instant) extends Model [Long,Token]  {

  override def updateModifiedField(): Token = this.copy(createdAt = Instant.now())
}

object Token {

  val TTL: Long = 5 * 60 * 1000

  implicit val format: OFormat[Token] = Json.format[Token]

  def tupled: ((Long, Long, String, Instant)) => Token = (this.apply _).tupled
}


