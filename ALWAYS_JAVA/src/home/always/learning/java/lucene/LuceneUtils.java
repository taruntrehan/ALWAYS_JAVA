package home.always.learning.java.lucene;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.google.gson.Gson;

public class LuceneUtils {
	
	public static Map<String, String> getMetricsAsJson(String filePath , String fileName , String splitBy){
		
		Map<String,String> metricsMap = new HashMap<String, String>();
		try {
			List<TagDetailsBean> metricDtlsList = new ArrayList<TagDetailsBean>();
			
			FileInputStream fis = new FileInputStream(filePath + fileName);
			Scanner scanner = new Scanner(fis);
			Gson coreGson = new Gson();
			while (scanner.hasNextLine()) {
				String currLine = scanner.nextLine();
				System.out.println("Current Read Line is:"+currLine);
				TagDetailsBean metricObj = new TagDetailsBean();
				String[] currSplit = currLine.split(splitBy);
				System.out.println("Current Split is:"+currSplit);
				System.out.println("Current title is:"+currSplit[0]);
				System.out.println("Current link is:"+currSplit[1]);
				metricObj.setMetricName(currSplit[0]);
				metricObj.setMetricDescription(currSplit[1]);
				metricsMap.put(metricObj.getMetricName(), coreGson.toJson(metricObj));
				metricDtlsList.add(metricObj);
			}
			System.out.println("The size of list Is:" + metricDtlsList.size());
			fis.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return metricsMap;
	}
	
	public static List<PostDetailsBean> readMetricsXls(String filePath , String fileName)
	{
		List<PostDetailsBean> reportsDtlsList = new ArrayList<PostDetailsBean>();
		try {
		//Create Workbook instance holding reference to .xlsx file
		FileInputStream fis = new FileInputStream(filePath + fileName);
        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        //Get first/desired sheet from the workbook
        XSSFSheet sheet = workbook.getSheetAt(0);
		
			Iterator<Row> rowIterator = sheet.iterator();
            while (rowIterator.hasNext()) 
            {
                Row row = rowIterator.next();
                PostDetailsBean reportMetricObj = new PostDetailsBean();
                System.out.println("Reading 1st column:"+row.getCell(0));
                System.out.println("Reading 2nd column:"+row.getCell(1));
                System.out.println("Reading 3rd column:"+row.getCell(2));
                System.out.println("Reading 4th column:"+row.getCell(3));
                reportMetricObj.setReportTitle(row.getCell(0).toString());
                reportMetricObj.setReportLink(row.getCell(1).toString());
                reportMetricObj.setReportDescription(row.getCell(2).toString());
                reportMetricObj.setReportMetrics(row.getCell(3).toString());
                reportsDtlsList.add(reportMetricObj);
            }
			System.out.println("The Size of list is:" + reportsDtlsList.size());
			fis.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
		return reportsDtlsList;
	}
	
public static void getReportDetailsAsJson(String filePath , String fileName , String splitBy){
		
		try {
			List<PostDetailsBean> reportsDtlsList = new ArrayList<PostDetailsBean>();
			
			FileInputStream fis = new FileInputStream(filePath + fileName);
			Scanner scanner = new Scanner(fis);

			while (scanner.hasNextLine()) {
				String currLine = scanner.nextLine();
				System.out.println("Current Read Line is:"+currLine);
				PostDetailsBean reportMetricObj = new PostDetailsBean();
				String[] currSplit = currLine.split(splitBy);
				System.out.println("Current title is:"+currSplit[0]);
				System.out.println("Current link is:"+currSplit[1]);
				System.out.println("Current desc is:"+currSplit[2]);
				System.out.println("Current metric is:"+currSplit[3]);
				reportMetricObj.setReportTitle(currSplit[0]);
				reportMetricObj.setReportLink(currSplit[1]);
				reportMetricObj.setReportDescription(currSplit[2]);
				reportMetricObj.setReportMetrics(currSplit[3]);
				reportsDtlsList.add(reportMetricObj);
			}
			System.out.println("The Size of list is:" + reportsDtlsList.size());
			fis.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
	public static String readFileToString(String filePath, String fileName) {
		StringBuffer fileContentsBuff = new StringBuffer();
		try {
			System.out.println("readFileToString(): Write file " + filePath + File.pathSeparator + fileName + " - Begin");
			FileInputStream fis = new FileInputStream(filePath + fileName);
			Scanner scanner = new Scanner(fis);

			// reading file line by line using Scanner in Java
			System.out.println("Reading file line by line in Java using Scanner");

			while (scanner.hasNextLine()) {
				fileContentsBuff.append(scanner.nextLine());
			}
			System.out.println("The Lenght of Json String To Be Returned Is:" + fileContentsBuff.toString().length());
			fis.close();
			scanner.close();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} catch (Exception e) {
			e.printStackTrace();
		}
		return fileContentsBuff.toString();
	}

}
