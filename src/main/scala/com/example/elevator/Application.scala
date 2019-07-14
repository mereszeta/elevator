package com.example.elevator

object Application extends App {
  try {
    val (numberOfElevators, numberOfFloors) = this.parseInitialArg(args)
    val elevatorSystem: ElevatorSystem = new SimpleElevatorSystem(numberOfElevators)
    this.runProgramLoop(elevatorSystem, numberOfElevators, numberOfFloors)
  } catch {
    case _: NumberFormatException | _: IllegalArgumentException =>
      this.printRunInfoAndExit("Invalid argument for number of elevators")
    case _: ArrayIndexOutOfBoundsException =>
      this.printRunInfoAndExit("No parameter provided")
  }

  private def parseInitialArg(strings: Array[String]): (Int, Int) = {
    val numberOfElevators = args(0).toInt
    val numberOfFloors = args(1).toInt
    if (!(1 to 16 contains numberOfElevators) || numberOfFloors < 0) {
      throw new IllegalArgumentException
    }
    (numberOfElevators, numberOfFloors)
  }

  private def printRunInfoAndExit(exceptionArg: String): Unit = {
    println(s"Exception encountered: $exceptionArg")
    println("To run application type: sbt \"run [number_of_elevators] [number of floors]\" Number of elevators must be between 1 and 16")
    System.exit(0)
  }

  private def runProgramLoop(system: ElevatorSystem, numberOfElevators: Int, numberOfFloors: Int): Unit = {
    Iterator.continually(io.StdIn.readLine).takeWhile(_ != "exit").foreach {
      case "status" =>
        println("System status: ")
        println(system.status())
        system.step()
      case message if message.startsWith("pickup") =>
        val argsList = message.split(" ")
        this.handlePickup(argsList, system, numberOfFloors)
      case message if message.startsWith("update") =>
        val argsList = message.split(" ")
        this.handleUpdate(argsList, system, numberOfElevators, numberOfFloors)
      case "help" =>
        this.printUsage()
        system.step()
      case _ =>
        this.printUsage()
        system.step()
    }
  }

  private def handlePickup(args: Array[String], system: ElevatorSystem, numberOfFloors: Int): Unit = {
    try {
      val sourceFloor = args(1).toInt
      val directionOption: Direction.Value = Direction.getByName(args(2)).getOrElse(throw IllegalArgumentException)
      if (sourceFloor < 0 || sourceFloor > numberOfFloors) {
        throw new IllegalArgumentException
      }
      system.pickup(sourceFloor, directionOption)
    } catch {
      case _: Throwable =>
        this.printUsage()
    }
  }

  private def handleUpdate(args: Array[String], system: ElevatorSystem, numberOfElevators: Int, numberOfFloors: Int): Unit = {
    try {
      val elevatorId = args(1).toInt
      val currentFloor = args(2).toInt
      val targetFloors: Array[Int] = args.drop(3).map(arg => arg.toInt).filter(floor => 0 to numberOfFloors contains floor)
      if (!(0 to numberOfElevators contains elevatorId) || !(0 to numberOfFloors contains currentFloor) || targetFloors.isEmpty) {
        throw IllegalArgumentException
      }
      system.update(elevatorId, currentFloor, targetFloors.toList)
    } catch {
      case _: Throwable =>
        this.printUsage()
    }
  }

  private def printUsage(): Unit = {
    println(
      """Those options are available:
        |exit - terminates program
        |status - displays position of each elevator
        |pickup [floor] [direction] - calls elevator to specified floor with specified direction
        |update [elevator_id] [current_floor] [target_floor...] - updates information about specified elevator
        |help - displays this message"""".stripMargin)
  }
}
