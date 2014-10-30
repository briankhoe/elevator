import java.util.List;
import java.util.ArrayList;

public class Building extends AbstractBuilding {

	private Elevator[] elevators;
	private List<List<Rider>> ridersOutside;

	public Building(int numFloors, int numElevators, int maxOccupancyThreshold) {
		super(numFloors, numElevators);
		ridersOutside = new ArrayList<List<Rider>>();
		for(int j = 0; j < numFloors; j++) {
			ridersOutside.add(new ArrayList<Rider>());
		}
		elevators = new Elevator[numElevators];
		for(int i = 0; i < numElevators; i++) {
			elevators[i] = new Elevator(numFloors, i, maxOccupancyThreshold, ridersOutside);
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
		int bestElevator = findBestElevator(fromFloor, -1);
		Elevator curElevator = elevators[bestElevator];
		ridersOutside.get(fromFloor).add(curRider);
		curElevator.addFloorToAppropriateQueue(fromFloor);
//		Elevator.enterEventBarriers[fromFloor].arrive();
		return curElevator;
	}

	public int findBestElevator(int fromFloor, int direction) {
		return 0;
	}

	public List<List<Rider>> getRidersOutsite() {
		return ridersOutside;
	}
	
	public Elevator getElevator() {
		return elevators[0];
	}

}