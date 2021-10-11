package services.commands.Impl

import daos.{PermissionDAO, RoleDAO, UserDAO}
import exceptions.Exceptions.UserRoleDoesNotExist
import models.{PermissionWrapper}
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
      user <- userDAO.getById(id = parameters.userId)
      userOrgId = user.flatMap(_.organization_id)
    } yield {
      (parameters.organization_id, parameters.permissionWrapper.isGlobal) match {
        case (Some(_), _) if role.hasGlobalAccess =>
          permissions.map(_.name).contains(permissionName)
        case (Some(organizationId), false) if userOrgId.contains(organizationId) =>
          permissions.map(_.name).contains(permissionName)
        case _ =>  permissions.map(_.name).contains(permissionName)
      }
    }
}

case class CheckPermissionParameters(userId: Long, organization_id: Option[Long], permissionWrapper: PermissionWrapper)

