/**
 * 
 */
package home.always.learning.java;



/**
 *
 */
public class YouThere implements Runnable {

	/*
	 * (non-Javadoc)
	 * 
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		System.out.println("Checking Server Status");
		DialServer.checkServer();
	}
}
