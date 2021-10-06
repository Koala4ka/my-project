package services.commands.Impl

import daos.{PermissionDAO, RoleDAO, UserDAO}
import exceptions.Exceptions.UserRoleDoesNotExist
import models.{Permission, PermissionWrapper}
import monix.eval.Task
import services.commands.Command

import javax.inject.Inject


class CheckPermissionCommand @Inject()(permissionDAO: PermissionDAO,
                                       roleDAO: RoleDAO,
                                       userDAO: UserDAO) extends Command[CheckPermissionParameters] {

  override type RT = Boolean

  override def execute(parameters: CheckPermissionParameters): Task[Boolean] =
    for {
      role <- roleDAO.getByUserId(userId = parameters.userId)
        .map(_.getOrElse(throw UserRoleDoesNotExist))
      roleId = role.id
      permissions <- permissionDAO.getByRoleId(roleId)
      permissionName = parameters.permissionWrapper.permissionName
      user <- userDAO.getById(id = 0)
      organizationId = user.flatMap(_.organization_id)
    } yield {
      parameters.permissionWrapper.isGlobal match {
        case true if role.hasGlobalAccess =>
          permissions.map(_.name).contains(permissionName)
        case false =>
          organizationId == parameters.organization_id &&
            permissions.map(_.name).contains(permissionName)
      }
    }


}

case class CheckPermissionParameters(userId: Long, organization_id: Option[Long], permissionWrapper: PermissionWrapper)

