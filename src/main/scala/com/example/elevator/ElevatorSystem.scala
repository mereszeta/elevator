package com.example.elevator

trait ElevatorSystem {

  def pickup(sourceFloor: Int, direction: Direction.Value): Unit

  def update(elevatorId: Int, currentFloor: Int, targetFloor: List[Int], empty: Boolean = false): Unit

  def step(): List[SingleElevatorState]

  def status(): List[SingleElevatorState]
}
