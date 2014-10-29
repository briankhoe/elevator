import java.util.List;
import java.util.ArrayList;

public class Building extends AbstractBuilding {

  private List<Elevator> elevators;

  public Building(int numFloors, int numElevators, int maxOccupancyThreshold) {
    super(numFloors, numElevators);
    elevators = new ArrayList<Elevator>();
    for(int i = 0; i < numElevators; i++) {
      elevators.add(new Elevator(numFloors, i, maxOccupancyThreshold));
    }
  }

  @Override
  public synchronized AbstractElevator CallUp(int fromFloor) {
    int bestElevator = findBestElevator(fromFloor, 1);
    elevators.get(bestElevator).addFloorToAppropriateQueue(fromFloor);
  }

  @Override
  public synchronized AbstractElevator CallDown(int fromFloor) {
    int bestElevator = findBestElevator(fromFloor, -1);
    elevators.get(bestElevator).addFloorToAppropriateQueue(fromFloor);
  }

  public int findBestElevator(int fromFloor, int direction) {
    Elevator[] tempElevators = elevators.toArray(new Elevator[elevators.size()]);
    Arrays.sort(tempElevators);
    return tempElevators[0].elevatorId;
  }

}