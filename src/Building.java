import java.util.List;
import java.util.ArrayList;

public class Building extends AbstractBuilding {

  private Elevator[] elevators;

  public Building(int numFloors, int numElevators, int maxOccupancyThreshold) {
    super(numFloors, numElevators);
    elevators = new Elevator[numFloors];
    for(int i = 0; i < numElevators; i++) {
      elevators[i] = new Elevator(numFloors, i, maxOccupancyThreshold);
    }
  }

  @Override
  public synchronized AbstractElevator CallUp(int fromFloor) {
    System.out.println("Rider has called elevator up from " + fromFloor);
    int bestElevator = findBestElevator(fromFloor, 1);
    Elevator curElevator = elevators[bestElevator];
    curElevator.addFloorToAppropriateQueue(fromFloor);
    return curElevator;
  }

  @Override
  public synchronized AbstractElevator CallDown(int fromFloor) {
    System.out.println("Rider has called elevator down from " + fromFloor);
    int bestElevator = findBestElevator(fromFloor, -1);
    Elevator curElevator = elevators[bestElevator];
    curElevator.addFloorToAppropriateQueue(fromFloor);
    return curElevator;
  }

  public int findBestElevator(int fromFloor, int direction) {
    return 0;
  }

}