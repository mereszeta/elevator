package com.example.elevator

class SimpleElevatorSystem(val numberOfElevators: Int) extends ElevatorSystem {
  private var elevatorEngineState: List[SingleElevatorState] = this.generateInitialState(numberOfElevators)

  override def pickup(sourceFloor: Int, direction: Direction.Value): Unit = {
    val chosenElevator: SingleElevatorState = this.getNearestElevator(sourceFloor, direction)
    this.update(chosenElevator.elevatorIndex(), chosenElevator.currentFloor, List(sourceFloor), empty = true)
  }

  override def update(elevatorId: Int, currentFloor: Int, targetFloor: List[Int], empty: Boolean = false): Unit = {
    val elevator = elevatorEngineState(elevatorId)
    elevatorEngineState = elevatorEngineState.updated(elevatorId, new SingleElevatorState(elevatorId, currentFloor, elevator.targetFloor.appendedAll(targetFloor), empty))
  }

  override def step(): List[SingleElevatorState] = {
    elevatorEngineState = elevatorEngineState.map(elem => this.updateSingleElevatorState(elem))
    elevatorEngineState
  }

  override def status(): List[SingleElevatorState] = elevatorEngineState

  private def generateInitialState(numberOfElevators: Int): List[SingleElevatorState] =
    List.tabulate(numberOfElevators)(index => new SingleElevatorState(index, 0, List.empty, true))

  private def updateSingleElevatorState(singleElevatorState: SingleElevatorState): SingleElevatorState = {
    if (singleElevatorState.targetFloor.nonEmpty) {
      val floorDiff = this.calculateFloorDiff(singleElevatorState.currentFloor, singleElevatorState.targetFloor(0))
      if (floorDiff == 0) {
        this.handleElevatorArrived(singleElevatorState)
      }
      else {
        singleElevatorState.currentFloor += floorDiff
        singleElevatorState
      }
    }
    else {
      singleElevatorState
    }
  }

  private def calculateFloorDiff(currentFloor: Int, targetFloor: Int): Int = {
    if (currentFloor > targetFloor) -1 else if (currentFloor < targetFloor) 1 else 0
  }

  private def handleElevatorArrived(elevatorState: SingleElevatorState): SingleElevatorState = {
    if (elevatorState.empty) {
      println(s"Elevator ${elevatorState.elevatorIndex()} arrived on floor ${elevatorState.targetFloor} and is ready for use")
    }
    else {
      println(s"Elevator ${elevatorState.elevatorIndex()} arrived on floor ${elevatorState.targetFloor} and passenger left")
      if (elevatorState.targetFloor.isEmpty) {
        elevatorState.empty = true
      }
    }
    elevatorState.targetFloor = elevatorState.targetFloor.drop(1)
    elevatorState
  }

  private def getNearestElevator(sourceFloor: Int, direction: Direction.Value): SingleElevatorState = {
    val filteredList = this.elevatorEngineState.filter(elevator => this.emptyOrHeadingInDirection(elevator, direction))
    if (filteredList.isEmpty) {
      this.elevatorEngineState(0)
    }
    else {
      filteredList.min(Ordering.by((elem: SingleElevatorState) => math.abs(elem.currentFloor - sourceFloor)))
    }
  }

  private def emptyOrHeadingInDirection(elevator: SingleElevatorState, direction: Direction.Value): Boolean = {
    (elevator.empty && elevator.targetFloor.isEmpty) || (!elevator.empty && direction == this.calculateDirection(elevator))
  }

  private def calculateDirection(state: SingleElevatorState): Direction.Value = {
    if (state.currentFloor < state.targetFloor(0)) Direction.UP else Direction.DOWN
  }
}
