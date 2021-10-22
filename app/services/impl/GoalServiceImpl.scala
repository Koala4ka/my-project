package services.impl

import daos.GoalDAO
import models.Goal
import models.dtos.answers.GoalDTO
import models.dtos.question.CreateGoalForm
import monix.eval.Task
import services.GoalService

import java.time.Instant
import javax.inject.Inject

class GoalServiceImpl @Inject()(goalDAO: GoalDAO) extends GoalService {

  override def create(createGoalForm: CreateGoalForm): Task[GoalDTO] =
   goalDAO.create(Goal(id = 0,
     employeeId = createGoalForm.employeeId.toLong,
     name = createGoalForm.name,
     info = createGoalForm.info,
     createdAt = Instant.now,
     updatedAt = Instant.now))
     .map(_.toDTO)

}
