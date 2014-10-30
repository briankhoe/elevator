import java.util.List;
import java.util.ArrayList;
import java.util.Random;

public class Building extends AbstractBuilding {

	private Elevator[] elevators;
	private List<List<Rider>> ridersOutside;
  public static EventBarrier[] enterEventBarriers;

	public Building(int numFloors, int numElevators, int maxOccupancyThreshold) {
		super(numFloors, numElevators);
    enterEventBarriers = new EventBarrier[numFloors];
		ridersOutside = new ArrayList<List<Rider>>();
		for(int j = 0; j < numFloors; j++) {
			ridersOutside.add(new ArrayList<Rider>());
      enterEventBarriers[j] = new EventBarrier();
		}
		elevators = new Elevator[numElevators];
		for(int i = 0; i < numElevators; i++) {
			elevators[i] = new Elevator(numFloors, i, maxOccupancyThreshold, ridersOutside, enterEventBarriers);
		}
	}

	@Override
	public AbstractElevator CallUp(int fromFloor) {
		Rider curRider = (Rider) Thread.currentThread();
		System.out.println("Rider " + curRider.getRiderId() +" has called elevator up from " + fromFloor + " to " + curRider.getDesiredFloor());
		int bestElevator = findBestElevator(fromFloor, 1);
		Elevator curElevator = elevators[bestElevator];
//		System.out.println("Elevator called is " + curElevator.elevatorId);
		curElevator.addFloorToAppropriateQueue(fromFloor);
//		synchronized (this) {
//			Elevator.enterEventBarriers[fromFloor].arrive();
//		}
		ridersOutside.get(fromFloor).add(curRider);
		return curElevator;
	}

	@Override
	public AbstractElevator CallDown(int fromFloor) {
		Rider curRider = (Rider) Thread.currentThread();
		System.out.println("Rider " + curRider.getRiderId() +" has called elevator down from " + fromFloor + " to " + curRider.getDesiredFloor());
		int bestElevator = findBestElevator2(fromFloor, -1);
		Elevator curElevator = elevators[bestElevator];
		ridersOutside.get(fromFloor).add(curRider);
		curElevator.addFloorToAppropriateQueue(fromFloor);
//		Elevator.enterEventBarriers[fromFloor].arrive();
		return curElevator;
	}

  public int findBestElevator2(int fromFloor, int direction) {
    Random rand = new Random();
    int randNum = rand.nextInt(numElevators);
    return randNum;
  }

	public int findBestElevator(int fromFloor, int direction) {
    List<Elevator> elevatorsGoingInSameDirection = new ArrayList<Elevator>();
    List<Elevator> elevatorsNotGoingInSameDirection = new ArrayList<Elevator>();
    // Separate elevators into:
    // elevators going in the same direction as the call and that will pass the fromFloor on its path
    // and other elevators
    for(int i = 0; i < elevators.length; i++) {
      Elevator curElevator = elevators[i];
      if(fromFloor >= curElevator.getCurrentFloor() && direction == 1 && elevator.getCurrentDirection() >= 0 ||
         (fromFloor <= curElevator.getCurrentFloor() && direction == -1 && elevator.getCurrentDirection <= 0)) {
        elevatorsGoingInSameDirection.add(curElevator);
      }
      else {
        elevatorsNotGoingInSameDirection.add(curElevator);
      }
    }
    // Out of elevators going in the same direction, find the closest one
    int minDistance = Integer.MAX_VALUE;
    Elevator bestElevator = null;
    for(int j = 0; j < elevatorsGoingInSameDirection.size(); j++) {
      Elevator tempElevator = elevatorsGoingInSameDirection.get(j);
      if(tempElevator.getOccupancy() < tempElevator.getMaxOccupancy()) {
        if(Math.abs(fromFloor - tempElevator.getCurrentFloor()) < minDistance) {
          bestElevator = tempElevator;
        }
      }
    }
    // If an elevator going in the same direction doesn't exist,
    // find the elevator going in the opposite direction with max distance (will turn around/become stationary sooner)
    if(bestElevator == null) {
      int maxDistance = Integer.MIN_VALUE;
      for(int k = 0; k < elevatorsNotGoingInSameDirection.size(); k++) {
        Elevator tempElevator2 = elevatorsNotGoingInSameDirection.get(k);
        if(tempElevator2.getOccupancy < tempElevator2.getMaxOccupancy()) {
          if(Math.abs(fromFloor - tempElevator2.getCurrentFloor()) > maxDistance) {
            bestElevator = tempElevator2;
          }
        }
      }
    }
    // If it's still null, just return 0 because there's currently no available elevator that is not filled to capacity
    if(bestElevator == null) {
      return 0;
    }
    return bestElevator.getElevatorId();
	}

	public List<List<Rider>> getRidersOutsite() {
		return ridersOutside;
	}
	
	public Elevator getElevator() {
		return elevators[0];
	}

}