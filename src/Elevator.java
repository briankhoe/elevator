import java.util.List;
import java.util.ArrayList;

public class Elevator extends AbstractElevator {

  private EventBarrier[] exitEventBarriers;
  private EventBarrier[] enterEventBarriers;
  private int curFloor;
  private int curDirection;
  private int occupancy;
  private PriorityQueue<Integer> floorsIncreasing;
  private PriorityQueue<Integer> floorsDecreasing;
  private boolean isDoorOpen;
  private List<Rider> riders;
  private List<Rider>[] ridersOutside;

  public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, List<Rider>[] ridersOutside) {
    super(numFloors, elevatorId, maxOccupancyThreshold);
    exitEventBarriers = new EventBarrier[numFloors];
    enterEventBarriers = new EventBarrier[numFloors];
    for(int i = 0; i < numFloors; i++) {
      exitEventBarriers[i] = new EventBarrier();
      enterEventBarriers[i] = new EventBarrier();
    }
    curFloor = 0;
    curDirection = 0;
    occupancy = 0;
    floorsIncreasing = new PriorityQueue<Integer>(numFloors);
    floorsDecreasing = new PriorityQueue<Integer>(numFloors, Collections.reverseOrder());
    isDoorOpen = false;
    riders = new ArrayList<Rider>();
    this.ridersOutside = ridersOutside;
  }

  private void serveJobs() {
    while(!floorsIncreasing.isEmpty() || !floorsDecreasing.isEmpty()) {
      if(curDirection > 0) {
        while(!floorsIncreasing.isEmpty()) {
          VisitFloor(floorsIncreasing.poll());
        }
        curDirection = -1;
      }
      else {
        while(!floorsDecreasing.isEmpty()) {
          VisitFloor(floorsDecreasing.poll());
        }
        curDirection = 1;
      }
    }
    curDirection = 0;
  }

  @Override
  public void OpenDoors() {
    System.out.println("Doors opening");
    isDoorOpen = true;
    exitEventBarriers[curFloor].raise();
    enterEventBarriers[curFloor].raise();
    ClosedDoors();
  }

  @Override
  public void ClosedDoors() {
    System.out.println("Doors closing");
    isDoorOpen = false;
  }

  @Override
  public synchronized void VisitFloor(int floor) {
    System.out.println("Heading to floor " + floor);
    if(curFloor < floor) {
      for(int i = curFloor; i <= floor; i++) {
        System.out.println("On floor " + floor);
      }
    }
    if(curFloor > floor) {
      for(int i = curFloor; i >= floor; i--) {
        System.out.println("On floor " + floor);
      }
    }
    curFloor = floor;
    OpenDoors();
  }

  @Override
  public synchronized boolean Enter() {
    if(occupancy < maxOccupancyThreshold) {
      Rider curRider = (Rider) Thread.currentThread();
      riders.add(curRider);
      ridersOutside[curFloor].remove(curRider);
      occupancy++;
      enterEventBarriers[curFloor].complete();
      System.out.println("Rider " + curRider.getRiderId + " has entered the elevator");
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

  public EventBarrier getExitEventBarriers() {
    return exitEventBarriers;
  }

  public EventBarrier getEnterEventBarriers(int fromFloor) {
    return enterEventBarriers[fromFloor];
  }
}