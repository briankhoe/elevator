public class Rider extends Thread {
	// Each rider has own thread
	// When riders run, each rider gets list of entrances and exits
	// While input not null "follow your instructions"
	// try to get on elevator, request floor, exit, if you have another command, do it
	// Hardcode it in for now
	private int curFloor;
	private int desiredFloor;
	private int desiredDirection;
	private Building building;
	private int riderId;

	public Rider(int riderId, int desiredFloor, int curFloor, Building building) {
		this.curFloor = curFloor;
		this.desiredFloor = desiredFloor;
		if(desiredFloor > curFloor) {
			desiredDirection = 1;
		}
		else {
			desiredDirection = -1;
		}
		this.building = building;
		this.riderId = riderId;
	}
	@Override
	public void run() {
		Elevator elevator;
		if(desiredFloor > curFloor) {
			elevator = (Elevator) building.CallUp(curFloor);
			desiredDirection = 1;
		}
		else {
			elevator = (Elevator) building.CallDown(curFloor);
			desiredDirection = -1;
		}
		elevator.getEnterEventBarriers(curFloor).arrive();
		elevator.Enter();
		elevator.RequestFloor(desiredFloor);
		elevator.getExitEventBarriers().arrive();
		elevator.Exit();
	}

	public int getDesiredFloor() {
		return desiredFloor;
	}

	public int getDesiredFloorDirection() {
		return desiredDirection;
	}

	public int getRiderId() {
		return riderId;
	}

	@Override
	public boolean equals(Object arg){
		if(this.getRiderId() == ((Rider) arg).getRiderId()){
			return true;
		}
		return false;
	}
}