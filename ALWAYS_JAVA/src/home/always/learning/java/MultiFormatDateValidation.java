/**
 * 
 */
package home.always.learning.java;

import java.io.BufferedReader;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStreamReader;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * @author Tarun Trehan
 * @category Utility
 * 
 * Code to validate if input "String" is a valid date or not.
 * In this scenario, there is no fixed format against which the validation is supposed to be done.
 * However, with JAVA , the easiest approach is to catch a Parse Exception.
 * But, for catching a parse exception; we need to test it against a specified format.
 * So, for this implementation, we prepare an array that contains the formats against which we will
 * perform validation test.
 * Next, we loop the entire list and test against each format.
 * If the validation fails, test for other formats till list iteration is completed.
 * If it passes for a given format, break the loop and exit.
 * 
 *  View the sample formats file under Input Folder.
 */

public class MultiFormatDateValidation {

	private static List<String> validFormatsList = null;
	/**
	 * @param args
	 */
	public static void main(String[] args) {
		String filePath = null;
		String dateVal = null;

		try {
			/*
			 * 
			 * Loading Pre-Defined Date Formats from File.
			 * 
			 */
			try
			{
				filePath = args[0].trim();
				System.out.println("FilePath Specified is"+filePath);
			} catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Oops.Expected a Input Parameter. Did not Find One. You should specify correct file name to proceed.");
			}
			
			/* Below Test Case is format and validity test.
			 * 2012 was a leap year and hence 29 is a valid date.
			 * Change year to 2013 and test.
			 */
			
			try
			{
				dateVal = args[1].trim();
				System.out.println("Date Value Specified for validation is"+dateVal);
			} catch(ArrayIndexOutOfBoundsException e)
			{
				System.out.println("Oops.Expected a Input Parameter. Did not Find One. You should specify date value to proceed.");
			}
			
			loadDateFormats(filePath);
			boolean validationResult = isDateValid(dateVal);
			if(!validationResult)
			{
				System.out.println("The Date provided i.e. "+dateVal+" has failed validation for pre-defined formats.");
			}
		} catch(ArrayIndexOutOfBoundsException e){
			System.out.println("Oops.Expected a Input Parameter. Did not Find One. You should specify correct file name to proceed.");
		}
		catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/*
	 * Core Function for validation check.
	 * 
	 */
	public static boolean isDateValid(String dateValue)
	{
		SimpleDateFormat sdfObj = new SimpleDateFormat();
		sdfObj.setLenient(false);
		boolean returnVal = false;
		/*
		 * Loop through list of formats and validate using JAVA API.
		 */
		String patternVal = null;
		for (int i = 0; i < validFormatsList.size(); i++) 
		{
			patternVal = new String(validFormatsList.get(i));
			try
			{
				sdfObj.applyPattern(patternVal);
				sdfObj.parse(dateValue);
				returnVal = true;
				System.out.println("Looks like a valid date for Date Value :"+dateValue+": For Format:"+patternVal);
				break;
			}
			catch(ParseException e){
				//System.out.println("Parse Exception Occured for Date Value :"+dateValue+":And Format:"+patternVal);
				//e.printStackTrace();
			}
			catch(Exception e){
				System.out.println("Generic Exception Occured While Parsing Date Value");
				e.printStackTrace();
			}
		}
		return returnVal;
	}

	/*
	 * 
	 * Method to load the date formats.
	 * These date formats are specified in a text file where each line represents a format.
	 * The input date should be validated against these formats.
	 * 
	 * */
	public static void loadDateFormats(String filePath)
	{
		FileInputStream fisDateFmObj = null;
		BufferedReader dateFmRdr = null;
		validFormatsList = new ArrayList<String>();
		try	{
			fisDateFmObj = new FileInputStream(filePath);
			dateFmRdr = new BufferedReader(new InputStreamReader(fisDateFmObj));
			String readLineStr = null;
			while((readLineStr = dateFmRdr.readLine())!=null)
			{
				readLineStr = new String(readLineStr.trim());
				//System.out.println("Date Format Specified In File : "+readLineStr);
				validFormatsList.add(readLineStr);
			}
		} catch (FileNotFoundException e) {
			System.out.println("File specifying date formats has not been found at the specified path.");
			e.printStackTrace();
		} catch (IOException e) {
			System.out.println("An IO Exception occured while readling formats file");
			e.printStackTrace();
		} catch (Exception e) {
			System.out.println("A Generic Exception Occured While Reading Formats File.");
			e.printStackTrace();
		}
		
		finally{
			try {
				if(fisDateFmObj!=null)
				{
					fisDateFmObj.close();
				}
			}catch(IOException e){
				System.out.println("Exception while closing File Input Stream.");
				e.printStackTrace();
			}
			finally
			{
				try {
					if(dateFmRdr!=null)
					{
						dateFmRdr.close();
					}
				} catch (IOException e) {
					System.out.println("Exception while closing Buffered Reader Stream.");
					e.printStackTrace();
				}
			}
		}
	}
}