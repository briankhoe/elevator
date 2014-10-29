import java.util.List;
import java.util.ArrayList;

public class Elevator extends AbstractElevator {

  private List<List<EventBarrier>> eventBarriers;
  private int curFloor;
  private int curDirection;
  private int occupancy;
  private PriorityQueue<Integer> floorsIncreasing;
  private PriorityQueue<Integer> floorsDecreasing;
  private boolean isDoorOpen;

  public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold) {
    super(numFloors, elevatorId, maxOccupancyThreshold);
    for(int i = 0; i < numFloors; i++) {
      eventBarriers.add(new EventBarrier());
    }
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
    eventBarriers.get(curFloor).raise();
  }

  @Override
  public synchronized void ClosedDoors() {
    isDoorOpen = false;
  }

  @Override
  public synchronized void VisitFloor(int floor) {
    curFloor = floor;
    OpenDoors();
  }

  @Override
  public synchronized boolean Enter() {
    if(occupancy < maxOccupancyThreshold) {
      occupancy++;
      return true;
    }
    return false;
  }
  // If over capacity, return fail, and call a new elevator

  @Override
  public synchronized void Exit() {
    if(occupancy > 0) {
      occupancy--;
    }
  }

  @Override
  public synchronized void RequestFloor(int floor) {
    addFloorToAppropriateQueue(floor);
  }

  public EventBarrier getEventBarrier() {
    return eventBarrier;
  }
  // Just make sure multiple riders calling add to queue aren't skipping floors
  public void addFloorToAppropriateQueue(int floor) {
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

  // While pq not empty visitFloor(floor), switch to other pq if empty, but if that's empty just change direction to 0
  private void serveJobs() {

  }
}