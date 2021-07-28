package resources;


import org.apache.commons.collections4.MultiValuedMap;
import org.apache.commons.collections4.multimap.ArrayListValuedHashMap;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;



public final class Utilities extends Base {
	//private static WebDriver driver;
  // private static JavascriptExecutor executor;
    
	public Utilities(WebDriver driver) {
		Utilities.driver = driver;
      //  Utilities.executor = (JavascriptExecutor) driver;
	}
	public static WebElement waitWebElement(WebElement a, int num) {
		WebDriverWait w= new WebDriverWait(driver,num);
		return w.until(ExpectedConditions.visibilityOf(a));
	}
	public static List<WebElement> listWait(By a, int num) {
		WebDriverWait w= new WebDriverWait(driver,num);
		return w.until(ExpectedConditions.numberOfElementsToBeMoreThan(a, 0));
	}
	
	public static void waitURL(String string, int num) {
		WebDriverWait w= new WebDriverWait(driver,num);
		w.until(ExpectedConditions.urlContains(string));
		}
	public static void jsClick (WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].click();", element);
	}
	

	public static ArrayList<String> getData(String excelpath, String testcaseName) throws IOException {
		
		ArrayList<String> a = new ArrayList<String>();
		ExcelUtils excel = new ExcelUtils (excelpath, testcaseName);
		int rowCount = excel.getRowCount();
		int colCount = excel.getColCount();
		for (int i=1; i<rowCount; i++) {
			for(int j=0; j<colCount; j++) {
				String cellData = excel.getCellData(i, j);
				a.add(cellData);
			}
		} return a;
	}
		public static ArrayList<String> getDataHeader(String excelpath, String testcaseName) throws IOException {
			
			ArrayList<String> a = new ArrayList<String>();
			ExcelUtils excel = new ExcelUtils (excelpath, testcaseName);
			int rowCount = excel.getRowCount();
			int colCount = excel.getColCount();
			for (int i=1; i<rowCount; i++) {
				for(int j=0; j<colCount; j++) {
					String cellData = excel.getCellData(0, j);
					a.add(cellData);
				}
			} return a;
		
		
	} 	
	
	public static HashMap<String, String> hashData (String excelPath, String sheetName, int colNum) throws IOException {
		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
		int rowCount = excel.getRowCount();
		int colCount = excel.getColCount();
		HashMap<String, String> data = new HashMap<String, String>();
		for (int i=1; i<rowCount; i++) {
			for(int j=0; j<colCount; j++) {
				XSSFCell cellData = excel.getXSSFCell(i, j);
				XSSFCell headColumn = excel.getXSSFCell(colNum, j);
				cellData.setCellType(CellType.STRING);
				headColumn.setCellType(CellType.STRING);				
				String cellString = cellData.toString();
				String columnString = headColumn.toString();
				data.put(columnString, cellString);
			}
		} return data;
	}
	
	public static MultiValuedMap<String, String> MapData (String excelPath, String sheetName) throws IOException {
		ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
		int colCount = excel.getColCount();
		MultiValuedMap<String, String> data = new ArrayListValuedHashMap<>();
			for(int j=0; j<colCount; j++) {
				XSSFCell cellData = excel.getXSSFCell(1, j);				
				XSSFCell headColumn = excel.getXSSFCell(0, j);
				cellData.setCellType(CellType.STRING);
				headColumn.setCellType(CellType.STRING);				
				String cellString = cellData.toString();
				String columnString = headColumn.toString();
				data.put(columnString, cellString);
		} return data;
	}
	public static  void invisibilityWebElement(WebElement a, int num) {
		WebDriverWait w= new WebDriverWait(driver,num);
		 w.until(ExpectedConditions.invisibilityOf(a));
	}
	
	public static WebElement elementReplace (String path,String value) {
		String finalPath =  path.replace("$value", value);
		WebElement element = driver.findElement(By.xpath(finalPath));
		return element;
	}
	
	public static void scrollToElement(WebElement element) {
		JavascriptExecutor executor = (JavascriptExecutor) driver;
		executor.executeScript("arguments[0].scrollIntoView(true);", element);

	}
	
	public static HashMap<String,String> date(String date) {
		String[] a = date.split("/");
		HashMap<String,String> dateData = new HashMap<String,String>();
		
		int monthInt = Integer.parseInt(a[1]);
		String monthString = monthString(monthInt);
		dateData.put("month", monthString);
		dateData.put("day", a[0]);
		dateData.put("year", a[2]);
		return dateData; 
	}
	
	private static  String monthString (Integer month ) {
        String monthString;
        switch (month) {
            case 1:  monthString = "January";
                     break;
            case 2:  monthString = "February";
                     break;
            case 3:  monthString = "March";
                     break;
            case 4:  monthString = "April";
                     break;
            case 5:  monthString = "May";
                     break;
            case 6:  monthString = "June";
                     break;
            case 7:  monthString = "July";
                     break;
            case 8:  monthString = "August";
                     break;
            case 9:  monthString = "September";
                     break;
            case 10: monthString = "October";
                     break;
            case 11: monthString = "November";
                     break;
            case 12: monthString = "December";
                     break;
            default: monthString = "Invalid month";
                     break;
        }
        return monthString;
	}
	
	public static List<String> loadPorpertiesList(String name) {
		String values = prop.get(name).toString();
		String[] listValues = values.split(",");
		List<String> list = Arrays.asList(listValues);
		return list;

	}
	
}
