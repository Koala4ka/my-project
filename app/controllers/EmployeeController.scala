package controllers

import controllers.utils.ControllerUtils
import daos.{EmployeeDAO, TokenDAO}
import models.PermissionWrapper
import models.dtos.question.CreateGoalForm
import monix.execution.Scheduler
import play.api.libs.json.Json
import play.api.mvc.{Action, AnyContent, ControllerComponents}
import services.{GoalService, JWTService}
import services.commands.Impl.CheckPermissionCommand


import javax.inject.{Inject, Singleton}
import scala.concurrent.ExecutionContext

@Singleton
class EmployeeController @Inject()(cc: ControllerComponents,
                                  jwtService: JWTService,
                                  tokenDAO: TokenDAO,
                                  checkPermissionCommand: CheckPermissionCommand,
                                   employeeDAO:EmployeeDAO,
                                   goalService: GoalService,
                                 )(implicit ex: ExecutionContext,
                                   sch: Scheduler)
  extends ControllerUtils(cc, jwtService, tokenDAO, checkPermissionCommand) {

  def createGoal(orgId:Option[Long]):Action[AnyContent] =
    authorizedActionPOST[CreateGoalForm](permissionWrapper = PermissionWrapper(permissionName = "Create-Goal",
      isGlobal = false),organizationId = orgId){
      req =>
        goalService.create(req.parsedBody)
          .map(dto => Ok(Json.toJson(dto)))
    }(CreateGoalForm.createGoalForm)

}
