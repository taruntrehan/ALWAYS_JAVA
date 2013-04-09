/**
 * 
 */
package home.always.learning.java;

/**
 * @author tarunruchi
 *
 */
public class WorkerThreadOne implements Runnable {

	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		
		try {
			System.out.println("The WorkerThreadOne has started processing....");
			Thread.sleep(10000L);
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}

}
