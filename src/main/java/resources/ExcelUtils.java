package resources;

import java.io.FileInputStream;
import java.io.IOException;

import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtils {
	
	private XSSFWorkbook workbook;
	private XSSFSheet sheet;
	
	public ExcelUtils(String excelPath, String sheetName) throws IOException {
		FileInputStream fis = new FileInputStream(excelPath);
		workbook = new XSSFWorkbook(fis);
		sheet = workbook.getSheet(sheetName);
		//sheet = getSheet(sheetName);
	}
	
	public XSSFSheet getSheet(String sheetName) {
		int sheets = workbook.getNumberOfSheets();
		for (int i=0; i<sheets; i++) {
				if (workbook.getSheetName(i).equalsIgnoreCase(sheetName)) {
				return sheet = workbook.getSheetAt(i);
				} 
				} return sheet;
	}
	
	public int getRowCount() throws IOException {
		
		int rowcount = sheet.getPhysicalNumberOfRows();
		return rowcount;
	}
	
	public int getColCount() throws IOException {
		
		int colcount = sheet.getRow(0).getPhysicalNumberOfCells();
		return colcount;
	}
	
	public String getCellData(int rowNum, int colnum) throws IOException {
		String cellData = sheet.getRow(rowNum).getCell(colnum).getStringCellValue();
		return cellData;
	}
	public XSSFCell getXSSFCell (int rownNum, int colnum) {
		XSSFCell cellData = sheet.getRow(rownNum).getCell(colnum);
		return cellData;
	}
	
	
	public void closeSheet() throws IOException {
		workbook.close();
	}
}
