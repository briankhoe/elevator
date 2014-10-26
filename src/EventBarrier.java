
public class EventBarrier extends AbstractEventBarrier {
	
	private int numThreads;
	private boolean isLocked;
	
	public EventBarrier() 
	{
		numThreads = 0;
		isLocked = false;
	}

	
	/**
	 * Arrive at the barrier and wait until an event is signaled. Return
 	 * immediately if already in the signaled state.
 	 */
	@Override
	public synchronized void arrive()
	{
		System.out.println("Thread: " + Thread.currentThread().getId() + " arrived at event barrier");
		numThreads++;
		while(!isLocked)
		{
			try
			{
				this.wait(); //wait until event is signaled
			}
			catch (InterruptedException e)
			{
				System.out.println("Thread: " + Thread.currentThread().getId() + " was interrupted");
				e.printStackTrace();
			}
		}
		return;
	}

	/**s
	 * Signal the event and block until all threads that wait for this
 	 * event have responded. The EventBarrier returns to an unsignaled state
 	 * before raise() returns.
 	 */	
	@Override
	public synchronized void raise()
	{
		if (numThreads != 0)
		{
			isLocked = true;
		}
		
		while (isLocked)
		{
			notifyAll();
			try
			{
				System.out.println("Waking up all Threads");
				this.wait();
			}
			catch (InterruptedException e)
			{
				System.out.println("Thread: " + Thread.currentThread().getId() + " was interrupted");
				e.printStackTrace();
			}
		}
		isLocked = false; //return to unsignaled state before raise returns
		System.out.println("All Threads complete");
	}
	
	/**
	 * Indicate that the calling thread has finished responding to a
 	 * signaled event, and block until all other threads that wait for 
 	 * this event have also responded.
 	 */
	@Override
	public synchronized void complete()
	{
		numThreads --;
		System.out.println("Thread " + Thread.currentThread().getId() + " has completed running. " 
				+ numThreads + " Threads remain");
		
		if (numThreads == 0)
		{
			isLocked = false;
		}
		else
		{
			isLocked = true;
		}
		
		notifyAll();
		
	}

	/**
	 * Return a count of threads that are waiting for the event or that
 	 * have not responded yet.
 	 */
	@Override
	public synchronized int waiters()
	{
		return numThreads;
	}

}
