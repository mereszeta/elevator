package com.example.elevator

class SingleElevatorState(private val _elevatorIndex: Int, var currentFloor: Int, var targetFloors: List[Int], var empty: Boolean) {
  def elevatorIndex(): Int = _elevatorIndex

  override def toString: String = {
    s"Index: ${this._elevatorIndex} currentFloor:${this.currentFloor} targetFloors:${this.targetFloors.concat(" ")} empty: ${if (this.empty) "yes" else "no"}"
  }
}
