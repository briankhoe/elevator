public class Elevator extends AbstractElevator {

  private EventBarrier eventBarrier;
  private int curFloor;
  private int curDirection;
  private int occupancy;
  private PriorityQueue<Integer> floorsIncreasing;
  private PriorityQueue<Integer> floorsDecreasing;
  private boolean isDoorOpen;

  public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
    super(numFloors, elevatorId, maxOccupancyThreshold);
    eventBarrier = new EventBarrier();
    curFloor = 0;
    curDirection = 0;
    occupancy = 0;
    floorsIncreasing = new PriorityQueue<Integer>(numFloors);
    floorsDecreasing = new PriorityQueue<Integer>(numFloors, Collections.reverseOrder());
    isDoorOpen = false;
  }

  @Override
  public synchronized void OpenDoors() {
    isDoorOpen = true;
  }

  @Override
  public synchronized void ClosedDoors() {
    isDoorOpen = false;
  }

  @Override
  public synchronized void VisitFloor(int floor) {

  }

  @Override
  public synchronized boolean Enter() {
    if(occupancy < maxOccupancyThreshold) {
      occupancy++;
      return true;
    }
    return false;
  }

  @Override
  public synchronized void Exit() {
    if(occupancy > 0) {
      occupancy--;
    }
  }

  @Override
  public synchronized void RequestFloor(int floor) {
    if(floorsIncreasing.contains(floor) || floorsDecreasing.contains(floor)) {
      return;
    }
    if((floor >= curFloor && curDirection >= 0) || (floor < curFloor && curDirection < 0)) {
      floorsIncreasing.add(floor);
    }
    else if((floor >= curFloor && curDirection < 0) || (floor < curFloor && curDirection >= 0)) {
      floorsDecreasing.add(floor);
    }
  }

  public EventBarrier getEventBarrier() {
    return eventBarrier;
  }
}