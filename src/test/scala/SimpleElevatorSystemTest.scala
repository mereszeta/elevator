import com.example.elevator.SimpleElevatorSystem
import com.example.elevator.Direction
import org.scalatest.FunSuite

class SimpleElevatorSystemTest extends FunSuite {
  test("Elevator system state should have length equal to number of elevators") {
    val numberOfElevators = 5
    val elevatorSystem = new SimpleElevatorSystem(numberOfElevators)
    assert(elevatorSystem.status().length == numberOfElevators)
  }
  test("After pickup one of the elevators should have updated targets and should be empty") {
    val numberOfElevators = 5
    val elevatorSystem = new SimpleElevatorSystem(numberOfElevators)
    elevatorSystem.pickup(3, Direction.UP)
    assert(elevatorSystem.status().exists(elevator => elevator.targetFloors.nonEmpty && elevator.empty))
  }
  test("After update one of the elevators should have updated targets and be non empty"){
    val numberOfElevators = 5
    val elevatorSystem = new SimpleElevatorSystem(numberOfElevators)
    elevatorSystem.update(0,0,List(2))
    assert(elevatorSystem.status().exists(elevator => elevator.targetFloors.nonEmpty && !elevator.empty))
  }
  test("After finished pickup elevator should have empty targets an be marked as empty"){
    val numberOfElevators = 5
    val elevatorSystem = new SimpleElevatorSystem(numberOfElevators)
    elevatorSystem.pickup(0,Direction.UP)
    val elevatorId = elevatorSystem.status().find(elevator => elevator.targetFloors.nonEmpty && elevator.empty).get.elevatorIndex()
    elevatorSystem.step()
    val elevator = elevatorSystem.status()(elevatorId)
    assert(elevator.targetFloors.isEmpty && elevator.empty)
  }
  test("After finished update elevator should have empty targets and be marked as empty"){
    val numberOfElevators = 5
    val elevatorSystem = new SimpleElevatorSystem(numberOfElevators)
    elevatorSystem.update(0,0,List(0))
    val elevatorId = elevatorSystem.status().find(elevator => elevator.targetFloors.nonEmpty && !elevator.empty).get.elevatorIndex()
    elevatorSystem.step()
    val elevator = elevatorSystem.status()(elevatorId)
    assert(elevator.targetFloors.isEmpty && elevator.empty)
  }
}
