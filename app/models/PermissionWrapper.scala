package models


case class PermissionWrapper(permissionName:String,
                             isGlobal:Boolean)


object PermissionWrapper {

  def tupled: (( String,Boolean)) => PermissionWrapper = (PermissionWrapper.apply _).tupled
}