package services

import models.dtos.answers.{GoalDTO}
import models.dtos.question.{CreateGoalForm}
import monix.eval.Task

trait GoalService {

  def create(createGoalForm: CreateGoalForm): Task[GoalDTO]

}
