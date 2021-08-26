package models

import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Organization(id: Long,
                        name: String,
                        address: String,
                        info: String,
                        createdAt: Instant,
                        updatedAt: Instant)

object Organization {
  implicit val format: OFormat[Organization] = Json.format[Organization]

  def tupled: ((Long, String, String, String, Instant, Instant)) => Organization = (Organization.apply _).tupled
}