package services.commands.Impl

import daos.{PermissionDAO, RoleDAO}
import exceptions.Exceptions.UserRoleDoesNotExist
import models.Permission
import monix.eval.Task
import services.commands.Command

import javax.inject.Inject


class CheckPermissionCommand @Inject ()(permissionDAO: PermissionDAO,
                                        roleDAO: RoleDAO)  extends Command[CheckPermissionParameters] {

  override type RT = Boolean

  override def execute(parameters: CheckPermissionParameters): Task[Boolean] =
    for {
      role <- roleDAO.getByUserId(userId = parameters.userId)
        .map(_.getOrElse(throw UserRoleDoesNotExist))
     roleId = role.id
      permissions <- permissionDAO.getByRoleId(roleId)
      permissionName = parameters.permission.name
    } yield  permissions.map(_.name).contains(permissionName)

}

case class CheckPermissionParameters(userId:Long, permission:Permission)

