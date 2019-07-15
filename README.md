# Elevator system 
## 1. About
This application is a symulation of coordination system for multiple elevators. It supports up to 16 elevators.
## 2. Prerequisites

- Scala v.2.13.0
- sbt (developed with v.1.2.8)
- git (developed with v.2.16.2)

## 3. Run

- clone repository:
```bash
git clone https://github.com/mereszeta/elevator.git
```
- run application:
```bash
sbt "run [number_of_elevators] [number_of_floors]"
```
- check usage:
```bash
help
```
- run tests:
```bash
sbt test
```
## Solution description
When elevator is ordered system, system is choosing closest elevator from set satisfying one of the criteria: Elevator is currently not used or elevator is used and heading in direction of a pickup.
 When elevator arrives, user can specify multiple target floors.