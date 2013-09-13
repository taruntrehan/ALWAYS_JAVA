/**
 * 
 */
package home.always.learning.java;


/**
 *
 */
public class YouSleeping implements Runnable{

	/**
	 * The following time denotes the duration after which this program will call to check server status.
	 * I have set to 30 minutes.
	 */
	private static final long retryPrimCallDur = 1800000;
	@Override
	public void run() 
	{
		System.out.println("Inside Core Thread For Server Sleep Status...");
		/*
		 * 
		 * Call Task for Checking Server.
		 * Go To Sleep After That.
		 * The calling thread is joined to the called one to ensure
		 * that child thread operation completes before parent
		 * proceeds further.
		 * 
		 */
		Thread checkThreadObj = null;
		while(true)
		{
			try {
				checkThreadObj = new Thread(new YouThere(), "CheckingYou");
				checkThreadObj.start();
				checkThreadObj.join();
				System.out.println("Check Server Completed");
				Thread.sleep(retryPrimCallDur);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
