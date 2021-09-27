package models

import models.dtos.PermissionDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Permission(id: Long=0,
                      name: String,
                      description: String="",
                      createdAt: Instant=Instant.now,
                      updatedAt: Instant=Instant.now
                     ) extends Model[Long, Permission] {

  def toDTO: PermissionDTO = PermissionDTO(id, name, description, createdAt, updatedAt)

  override def updateModifiedField(): Permission = this.copy(updatedAt = Instant.now())


}

object Permission {

  implicit val format: OFormat[Permission] = Json.format[Permission]

  def tupled: ((Long, String, String, Instant, Instant)) => Permission = (Permission.apply _).tupled
}
