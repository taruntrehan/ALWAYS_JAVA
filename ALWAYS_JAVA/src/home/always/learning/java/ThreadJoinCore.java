/**
 * 
 */
package home.always.learning.java;

/**
 * @author tarunruchi
 *
 */
public class ThreadJoinCore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Demonstrating Thread Join Feature");
			WorkerThreadOne wtOneObj = new WorkerThreadOne();
			WorkerThreadTwo wtTwoObj = new WorkerThreadTwo();
			
			Thread doProcOneObj = new Thread(wtOneObj, "WorkerThreadOne");
			Thread doProcTwoObj = new Thread(wtTwoObj, "WorkerThreadTwo");
			
			
			doProcOneObj.start();
			doProcTwoObj.start();
			doProcOneObj.join();
			doProcTwoObj.join();
			System.out.println("Process Ended After Both Threads Finished Their Tasks...");
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
	}

}
