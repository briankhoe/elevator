
public class ElevatorThread extends Thread{
	AbstractElevator elevator[];
	
	public ElevatorThread(Building building) {
		elevator = building.getElevator();
	}
	
	@Override
	public void run() {
		System.out.println("Before serve jobs");
		for(int i=0; i < elevator.length; i++)
		{
			((Elevator) elevator[i]).serveJobs();
		}
		System.out.println("After serve jobs");
	}
}
