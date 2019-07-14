package com.example.elevator

object Direction extends Enumeration {
  val UP, DOWN = Value

  def getByName(name: String): Option[Direction.Value] = values.find(_.toString == name)
}
