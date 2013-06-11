/**
 * 
 */
package home.always.learning.java;

import java.net.ConnectException;
import java.net.HttpURLConnection;
import java.net.URL;

/**
 * 
 * 
 * This code will ping server. If the status is fine, it will exit.
 * Else, it will sleep for X seconds and again ping for max attempts. If
 * All attempts fail, you can write code to alert e.g. SMS , MAIL , etc.
 * 
 * The feature of retry has been added to ensure that a server was unavailable
 * consistently over a period of time and hence an alarm should be raised.
 *
 *
 */

public class DialServer {

	private static final String url = "http://localhost";
	private static final String requestMethod = "GET";
	private static final int maxAttempts = 5;
	private static final int retryPeriod = 10000;

	public static void checkServer() {

		for (int i = 0; i <= maxAttempts; i++) {
			try {
				int responseCode = sendGet();
				if(responseCode==200)
				{
					System.out.println("Break Loop and Return");
					break;
				}
			} catch (ConnectException e) {
				if(i==maxAttempts)
				{
					System.out.println("Max Attempts Have Reached for Server Response...ALERT Now");
				}
				else
				{
					System.out.println("Connect Exception..Try Again Till Max Attempts...");
					try {
						Thread.currentThread().sleep(retryPeriod);
					} catch (InterruptedException e1) {
						// TODO Auto-generated catch block
						e1.printStackTrace();
					}
				}
			} catch (Exception e) {
				System.out.println("Generic Exception Connecting To Server...");
				e.printStackTrace();
			}
		}
	}

	private static int sendGet() throws Exception {
		URL obj = new URL(url);
		HttpURLConnection con = (HttpURLConnection) obj.openConnection();
		// optional default is GET
		con.setRequestMethod(requestMethod);
		System.out.println(con.getInputStream());
		int responseCode = con.getResponseCode();
		System.out.println("Response Code : " + responseCode);
		return responseCode;
	}
	
	private static void alertOwners()
	{
		
	}
}