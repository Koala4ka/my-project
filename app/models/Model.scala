package models


trait Model[ID , M <: Model[ ID, M]] extends HasId[ID] {

  def updateModifiedField(): M
}

trait HasId[ID] {
  val id: ID
}