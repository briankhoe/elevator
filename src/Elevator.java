import java.util.Collections;
import java.util.List;
import java.util.ArrayList;
import java.util.PriorityQueue;

public class Elevator extends AbstractElevator {

	private EventBarrier[] exitEventBarriers;
	// public static EventBarrier[] enterEventBarriers;
	private int curFloor;
	private int curDirection;
	private int occupancy;
	private PriorityQueue<Integer> floorsIncreasing;
	private PriorityQueue<Integer> floorsDecreasing;
	private boolean isDoorOpen;
	private List<Rider> riders;
	private List<List<Rider>> ridersOutside;

	public Elevator(int numFloors, int elevatorId, int maxOccupancyThreshold, List<List<Rider>> ridersOutside, EventBarrier[] enterEventBarriers) {
		super(numFloors, elevatorId, maxOccupancyThreshold);
		exitEventBarriers = new EventBarrier[numFloors];
		// enterEventBarriers = new EventBarrier[numFloors];
		for(int i = 0; i < numFloors; i++) {
			exitEventBarriers[i] = new EventBarrier();
			// enterEventBarriers[i] = new EventBarrier();
		}
		this.enterEventBarriers = enterEventBarriers;
		curFloor = 0;
		curDirection = 0;
		occupancy = 0;
		floorsIncreasing = new PriorityQueue<Integer>(numFloors);
		floorsDecreasing = new PriorityQueue<Integer>(numFloors, Collections.reverseOrder());
		isDoorOpen = false;
		riders = new ArrayList<Rider>();
		this.ridersOutside = ridersOutside;
	}

	public void serveJobs() {
		while(!floorsIncreasing.isEmpty() || !floorsDecreasing.isEmpty()) {
			if(curDirection > 0) {
				while(!floorsIncreasing.isEmpty()) {
					int temp = floorsIncreasing.poll();
//					System.out.println("Top of increasing pq is " + temp);
				
					VisitFloor(temp);
				}
				curDirection = -1;
			}
			else {
				while(!floorsDecreasing.isEmpty()) {
					int temp2 = floorsDecreasing.poll();
//					System.out.println("Top of decreasing pq is " + temp2);
					VisitFloor(temp2);
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
	public void VisitFloor(int floor) {
		System.out.println("Heading to floor " + floor);
		if(curFloor < floor) {
			for(int i = curFloor; i <= floor; i++) {
				System.out.println("On floor " + i);

			}
		}
		if(curFloor > floor) {
			for(int j = curFloor; j >= floor; j--) {
				System.out.println("On floor " + j);
			}
		}
		curFloor = floor;
		OpenDoors();
	}

	@Override
	public synchronized boolean Enter() {
//		if(occupancy < maxOccupancyThreshold) {
			Rider curRider = (Rider) Thread.currentThread();
			ridersOutside.get(curFloor).remove(curRider);
			riders.add(curRider);
			System.out.println("Rider " + curRider.getRiderId() + " has entered the elevator");
			enterEventBarriers[curFloor].complete();
			occupancy++;
			return true;
//		}
//		return false;
	}
	// If over capacity, return fail, and call a new elevator

	@Override
	public synchronized void Exit() {
		System.out.println("exits");
		Rider curRider = (Rider) Thread.currentThread();
		if(curFloor == curRider.getDesiredFloor()) {
			System.out.println("Rider " + curRider.getRiderId() + " exited the elevator");
			occupancy--;
			exitEventBarriers[curFloor].complete();
			riders.remove(curRider);
		}
	}

	@Override
	public synchronized void RequestFloor(int floor) {
		addFloorToAppropriateQueue(floor);
//		Rider curRider = (Rider) Thread.currentThread();
//		exitEventBarriers[curRider.getDesiredFloor()].arrive();
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
		Rider curRider = (Rider) Thread.currentThread();
		return exitEventBarriers[curRider.getDesiredFloor()];
	}

	public EventBarrier getEnterEventBarriers(int fromFloor) {
		return enterEventBarriers[fromFloor];
	}

	public int getOccupancy() {
		return occupancy;
	}

	public int getMaxOccupancy() {
		return maxOccupancyThreshold;
	}

	public int getElevatorId() {
		return elevatorId;
	}
}