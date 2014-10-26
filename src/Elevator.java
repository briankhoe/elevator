public class Elevator extends AbstractElevator {

  private EventBarrier eventBarrier;
  private int curFloor;
  private int curDirection;

  public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
    super(numFloors, elevatorId, maxOccupancyThreshold);
    eventBarrier = new EventBarrier();
    curFloor = 0;
  }

  @Override
  public void OpenDoors() {

  }

  @Override
  public void ClosedDoors() {

  }

  @Override
  public void VisitFloor(int floor) {

  }

  @Override
  public synchronized boolean Enter() {

  }

  @Override
  public synchronized void Exit() {

  }

  @Override
  public synchronized void RequestFloor(int floor) {

  }

  public EventBarrier getEventBarrier() {
    return eventBarrier;
  }
}