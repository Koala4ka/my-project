package models

import models.dtos.RoleDTO
import play.api.libs.json.{Json, OFormat}

import java.time.Instant

case class Role(id:Long,
                name:String,
                description:String,
                hasGlobalAccess:Boolean,
                createdAt: Instant,
                updatedAt: Instant ) extends Model [Long,Role] {

    def toDTO: RoleDTO = RoleDTO(id,name,description, hasGlobalAccess, createdAt, updatedAt)
    override def updateModifiedField(): Role = this.copy(updatedAt = Instant.now())
  }

  object Role {
    implicit val format: OFormat[Role] = Json.format[Role]

    def tupled: ((Long, String, String,Boolean, Instant, Instant)) => Role = (Role.apply _).tupled
  }


