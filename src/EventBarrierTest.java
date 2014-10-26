
public class EventBarrierTest 
{
	public static void test(){
		EventBarrier evBar1 = new EventBarrier();
		EventBarrier evBar2 = new EventBarrier();
		System.out.println("Created a new EventBarrier");
		for(int s = 0; s < 5; s++){
			Thread t = new helperThread(evBar1);
			t.start();
			System.out.println("Creating a new Thread: " + t.getId() + " at barrier 1");
		}
		for(int s = 0; s < 3; s++){
			Thread t = new helperThread(evBar2);
			t.start();
			System.out.println("Creating a new Thread: " + t.getId() + " at barrier 2");
		}
		
		System.out.println("Barrier 1 call raise from Thread " + Thread.currentThread().getId());
		evBar2.raise();
		evBar1.raise();
	}
}

class helperThread extends Thread {
	private EventBarrier myEB;
	public helperThread(EventBarrier eb) {
		myEB = eb;
	}
	public void run() {  
		myEB.arrive();
		myEB.complete();
	}
}