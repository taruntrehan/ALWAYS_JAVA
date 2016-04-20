package home.always.learning.java;

import java.util.List;

/*
 * This is thread core class.
 * It receives some call parameters from Thread Initiating class and performs desired tasks.
 * It then sets the result in instance variables.
 * These are consumed by calling class.
 * 
 */

public class WorkerThreadOne implements Runnable {
	
	// Parameter for call.
	private Long custIdParam = null;
	// Flag parameter to check the respective calls to Database
	private String callActionName = null;
	// Following are return types from database calls.
	private List resultsListFromBase = null;
	private List resultsListFromCore = null;
	
	public WorkerThreadOne(String callActionName, Long custIdParam) {
		super();
		this.callActionName = callActionName;
		this.custIdParam = custIdParam;
	}
	/**
	 * @return the custIdParam
	 */
	public Long getCustIdParam() {
		return custIdParam;
	}
	/**
	 * @param custIdParam the custIdParam to set
	 */
	public void setCustIdParam(Long custIdParam) {
		this.custIdParam = custIdParam;
	}
	/**
	 * @return the callActionName
	 */
	public String getCallActionName() {
		return callActionName;
	}
	/**
	 * @param callActionName the callActionName to set
	 */
	public void setCallActionName(String callActionName) {
		this.callActionName = callActionName;
	}
	/**
	 * @return the resultsListFromBase
	 */
	public List getResultsListFromBase() {
		return resultsListFromBase;
	}
	/**
	 * @param resultsListFromBase the resultsListFromBase to set
	 */
	public void setResultsListFromBase(List resultsListFromBase) {
		this.resultsListFromBase = resultsListFromBase;
	}
	/**
	 * @return the resultsListFromCore
	 */
	public List getResultsListFromCore() {
		return resultsListFromCore;
	}
	/**
	 * @param resultsListFromCore the resultsListFromCore to set
	 */
	public void setResultsListFromCore(List resultsListFromCore) {
		this.resultsListFromCore = resultsListFromCore;
	}
	/* (non-Javadoc)
	 * @see java.lang.Runnable#run()
	 */
	@Override
	public void run() {
		try 
		{
			System.out.println("The WorkerThreadOne has started processing....");
			if("BaseCall".equals(callActionName))
			{
				System.out.println("Fetching details for Base call for :"+Thread.currentThread().getName());
				//Fetch Details from Database in a list.
				// Assigning it to null for demonstration. In real life scenario, it will/may contain the data retrieved from database.
				List resultsListFromBase = null;
				this.setResultsListFromBase(resultsListFromBase);
				// For Thread JOIN demonstration purposes, i am making a call to sleep.
				Thread.sleep(10000L);
				System.out.println("Base Call Thread Finished Processing");
			}
			else if("CoreCall".equals(callActionName))
			{
				System.out.println("Fetching details for Core call for :"+Thread.currentThread().getName());
				//Fetch Details from Database in a list.
				// Assigning it to null for demonstration. In real life scenario, it will/may contain the data retrieved from database.
				List resultsListFromCore = null;
				this.setResultsListFromCore(resultsListFromCore);
				// For Thread JOIN demonstration purposes, i am making a call to sleep.
				Thread.sleep(30000L);
				System.out.println("Core Call Thread Finished Processing");
			} 
		} catch (InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
