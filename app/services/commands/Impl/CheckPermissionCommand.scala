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

  override def execute(parameters: CheckPermissionParameters): Task[Boolean] = {
    for {
      role <- roleDAO.getByUserId(userId = parameters.userId)
        .map(_.getOrElse(throw UserRoleDoesNotExist))
      roleId = role.id
      permissions <- permissionDAO.getByRoleId(roleId)
      permissionName = parameters.permissionWrapper.permissionName
      user <- userDAO.getById(id = parameters.userId)
      userOrgId = user.flatMap(_.organization_id)
    } yield {
      val hasPermission =  permissions.map(_.name).contains(permissionName)
      println(s"orgUserId = $userOrgId")
      println(s"param.organId =${parameters.organization_id}")
      println(s"Permission = $hasPermission")
      println("permissions")
      permissions.map(_.name).foreach(println)
      (parameters.organization_id, parameters.permissionWrapper.isGlobal) match {
        case (Some(_), _) if role.hasGlobalAccess => hasPermission
        case (Some(organizationId),false) if userOrgId.contains(organizationId)=>
        hasPermission
        case (Some(_), false) =>
          if (role.hasGlobalAccess) hasPermission
          else false
        case _=> false
      }
    }
  }
}

case class CheckPermissionParameters(userId: Long, organization_id: Option[Long], permissionWrapper: PermissionWrapper)

