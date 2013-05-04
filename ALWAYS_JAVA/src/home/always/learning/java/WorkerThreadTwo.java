/**
 * 
 */
package home.always.learning.java;


public class WorkerThreadTwo implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try {
			System.out.println("WorkerThreadTwo Processing....");
			Thread.sleep(30000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}

	}

}
