package services.commands

import models.Permission
import monix.eval.Task

trait Command [PT] {

  type RT

  def execute(parameters:PT): Task [RT]
}
