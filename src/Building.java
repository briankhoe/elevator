import java.util.List;
import java.util.ArrayList;

public class Building extends AbstractBuilding {

  private Elevator[] elevators;
  private List<Rider>[] ridersOutside;

  public Building(int numFloors, int numElevators, int maxOccupancyThreshold) {
    super(numFloors, numElevators);
    elevators = new Elevator[numFloors];
    ridersOutside = new List<Rider>[numFloors];
    for(int j = 0; j < numFloors; j++) {
      ridersOutside[j] = new ArrayList<Rider>();
    }
    for(int i = 0; i < numElevators; i++) {
      elevators[i] = new Elevator(numFloors, i, maxOccupancyThreshold, ridersOutside);
    }
  }

  @Override
  public synchronized AbstractElevator CallUp(int fromFloor) {
    System.out.println("Rider has called elevator up from " + fromFloor);
    int bestElevator = findBestElevator(fromFloor, 1);
    Elevator curElevator = elevators[bestElevator];
    curElevator.addFloorToAppropriateQueue(fromFloor);
    ridersOutside[fromFloor].add((Rider) Thread.currentThread());
    return curElevator;
  }

  @Override
  public synchronized AbstractElevator CallDown(int fromFloor) {
    System.out.println("Rider has called elevator down from " + fromFloor);
    int bestElevator = findBestElevator(fromFloor, -1);
    Elevator curElevator = elevators[bestElevator];
    ridersOutside[fromFloor].add((Rider) Thread.currentThread());
    curElevator.addFloorToAppropriateQueue(fromFloor);
    return curElevator;
  }

  public int findBestElevator(int fromFloor, int direction) {
    return 0;
  }

  public List<Rider>[] getRidersOutsite() {
    return ridersOutside;
  }

}