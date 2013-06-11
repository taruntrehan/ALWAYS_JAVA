/**
 * 
 */
package home.always.learning.java;


/**
 *
 */
public class YouSleeping implements Runnable{

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
				Thread.sleep(10000L);
			} catch (InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}
}
