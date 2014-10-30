
public class ElevatorThread extends Thread{
	AbstractElevator elevator;
	
	public ElevatorThread(Building building) {
		elevator = building.getElevator();
	}
	
	@Override
	public void run() {
		System.out.println("Before serve jobs");
		((Elevator) elevator).serveJobs();
		System.out.println("After serve jobs");
	}
}
