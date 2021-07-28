package resources;

import java.io.IOException;

public class ExcelDataProvider {
	
public Object[][] testData (String excelPath, String sheetName) throws IOException {
	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
	int rowCount = excel.getRowCount();
	int colCount = excel.getColCount();
	Object data[][] = new Object[rowCount-1][colCount];
	for (int i=1; i<rowCount; i++) {
		for(int j=0; j<colCount; j++) {
			String cellData = excel.getCellData(i, j);
			data[i-1][j] = cellData;
			excel.closeSheet();
		}
	} return data;
}

public Object[] testDataOne (String excelPath, String sheetName) throws IOException {
	ExcelUtils excel = new ExcelUtils(excelPath, sheetName);
	int rowCount = excel.getRowCount();
	int colCount = excel.getColCount();
	Object data[] = new Object[rowCount-1];
	for (int i=1; i<rowCount; i++) {
		for(int j=0; j<colCount; j++) {
			String cellData = excel.getCellData(i, j);
			data[i] = cellData;
			excel.closeSheet();
		}
	} return data;
}	

}
