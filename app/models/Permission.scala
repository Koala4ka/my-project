package models

object Permission extends Enumeration{
  type Permission = Value

  val MASTER  = Value("MASTER")
  val SENIOR  = Value("SENIOR")
  val OVERSEER = Value("OVERSEER")

  
}
