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
    return null;
  }

  @Override
  public synchronized AbstractElevator CallDown(int fromFloor) {
    return null;
  }

  public int findBestElevator() {
    return 0;
  }

}