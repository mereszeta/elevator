package com.example.elevator

class SingleElevatorState(private val _elevatorIndex: Int, var currentFloor: Int, var targetFloor: List[Int], var empty: Boolean) {
  def elevatorIndex(): Int = _elevatorIndex
}
