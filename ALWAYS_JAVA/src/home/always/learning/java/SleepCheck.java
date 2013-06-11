/**
 * 
 */
package home.always.learning.java;

/**
 * 
 * This program is intended to check the server status.
 * If the server is up; it returns and exits.
 * In case connection fails; it will retry connection for a 
 * pre-defined attempts count over a period of time.
 * 
 * 
 * SleepCheck is the Core Class to Start a never ending thread.
 * Thread will ping a server for its status.
 * Thread sleeps for a desired amount of time.
 * Wakes up and repeats.
 * 
 *
 */
public class SleepCheck {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		
		System.out.println("Sleep Check For Allzhere i.e. http://allzhere.in Server.");
		// This thread will never end.
		Thread sleepCheckInit = new Thread(new YouSleeping(), "SleepCheckInit");
		sleepCheckInit.start();
	}
}
