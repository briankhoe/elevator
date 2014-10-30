public class Rider implements Runnable {
  // Each rider has own thread
  // When riders run, each rider gets list of entrances and exits
  // While input not null "follow your instructions"
  // try to get on elevator, request floor, exit, if you have another command, do it
  // Hardcode it in for now
  private int curFloor;
  private int desiredFloor;
  private int desiredDirection;
  private Building building;
  private EventBarrier eventBarrier;
  private int riderId

  public Runner (riderId, building) {
    curFloor = 0;
    desiredDirection = 0;
    this.building = building;
    eventBarrier = new EventBarrier();
    this.riderId = riderId;
  }

  public void run() {
    AbstractElevator elevator;
    if(desiredFloor > curFloor) {
      elevator = building.CallUp(curFloor);
      desiredDirection = 1;
    }
    else {
      elevator = building.CallDown(curFloor);
      desiredDirection = -1;
    }
    elevator.getEnterEventBarriers(curFloor).arrive();
    elevator.Enter();
    elevator.RequestFloor(desiredFloor);
    elevator.Exit();
  }

  public int getDesiredFloor() {
    return desiredFloor;
  }

  public int getDesiredFloorDirection() {
    return desiredDirection;
  }

  public int getRiderId() {
    return riderId;
  }

  @Override
  public boolean equals(Object arg){
    if(this.getRiderId() == ((Rider) arg).getRiderId()){
      return true;
    }
    return false;
  }
}