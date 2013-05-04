
package home.always.learning.java;

/*
 * Consider this Class as your core calling class.
 * In our demonstration, this class is like a struts action class which calls
 * databases processes in parallel.
 * It then joins itself to each thread in order to ensure that we return from action class
 * once all threads have finished their respective tasks.
 */

public class ThreadJoinCore {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		try {
			System.out.println("Demonstrating Thread Join Feature");
			WorkerThreadOne wtOneObj = new WorkerThreadOne("BaseCall",1L);
			WorkerThreadOne wtTwoObj = new WorkerThreadOne("CoreCall",1L);
			
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
