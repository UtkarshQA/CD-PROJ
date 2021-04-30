package com.MyAccount.Utilities;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.FileReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.io.OutputStreamWriter;
import java.util.Iterator;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVPrinter;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormatter;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

public class ExcelUtil {
	
	 String cellData;

	    public String getDataFromExcel(String fileName, String sheetName, int cellNumber, int rowNumber)
	            throws IOException {
	        try {

	            DataFormatter fmt = new DataFormatter();
	            InputStream fis = new FileInputStream(new File(fileName));
	            XSSFWorkbook workbook = new XSSFWorkbook(fis);
	            XSSFSheet sheet = workbook.getSheet(sheetName);
	            XSSFRow row1 = sheet.getRow(rowNumber);
	            XSSFCell cell1 = row1.getCell(cellNumber);
	            String valueAsSeenInExcel = fmt.formatCellValue(cell1);
	            return valueAsSeenInExcel;
	            
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	        return String.valueOf(cellData);
	    }

	    public Integer getLastRow(String fileName, String sheetName) throws IOException {
	        int totalNoOfRowsExp = 0;
	        try {
	            FileInputStream file_expected = new FileInputStream(fileName);
	            XSSFWorkbook wb_Expected = new XSSFWorkbook(file_expected);
	            XSSFSheet sheet_Exp = wb_Expected.getSheet(sheetName);
	            totalNoOfRowsExp = sheet_Exp.getLastRowNum();
	            return totalNoOfRowsExp;
	        } catch (Exception e) {
	            // TODO: handle exception
	        }
	        return totalNoOfRowsExp;
	    }
	    
	    public void writeInExcel(String fileName, String sheetName, int cellNumber, int rowNumber, String text)
				throws IOException {

			FileInputStream file_expected = new FileInputStream(fileName);
			XSSFWorkbook workbook = new XSSFWorkbook(file_expected);
			XSSFSheet sheet = workbook.getSheet(sheetName);
			Cell cell = null;

			// Retrieve the row and check for null
			XSSFRow sheetrow = sheet.getRow(rowNumber);
			if (sheetrow == null) {
				sheetrow = sheet.createRow(rowNumber);
			}
			// Update the value of cell
			cell = sheetrow.getCell(cellNumber);
			if (cell == null) {
				cell = sheetrow.createCell(cellNumber);
			}

			cell.setCellValue(text);

			try {
				FileOutputStream outputStream = new FileOutputStream(fileName);
				workbook.write(outputStream);
				outputStream.close();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}

		}
	    
	    
	    public void xlsxToCSV(String xlsxfilePath, String csvfilePath) {
			CSVPrinter csvPrinter = null;
			try {

				InputStream fis = new FileInputStream(new File(xlsxfilePath));
				XSSFWorkbook workbook = new XSSFWorkbook(fis);
				OutputStream csv = new FileOutputStream(csvfilePath);

				csvPrinter = new CSVPrinter(new OutputStreamWriter(csv), CSVFormat.DEFAULT);

				if (workbook != null) {
					XSSFSheet sheet = workbook.getSheetAt(0); // Sheet #0 in this example
					Iterator<Row> rowIterator = sheet.rowIterator();
					while (rowIterator.hasNext()) {
						Row row = rowIterator.next();
						Iterator<Cell> cellIterator = row.cellIterator();
						while (cellIterator.hasNext()) {
							Cell cell = cellIterator.next();
							csvPrinter.print(cell.getStringCellValue());
						}
						csvPrinter.println(); // Newline after each row
					}
				}
		        System.out.println("--- xlsxToCSV Done -----");
			} catch (Exception e) {
				System.out.println("Failed to write CSV file to output stream :" + e);
			} finally {
				try {
					if (csvPrinter != null) {
						csvPrinter.flush(); // Flush and close CSVPrinter
						csvPrinter.close();
					}
				} catch (IOException ioe) {
					System.out.println("Error when closing CSV Printer" + ioe);
				}
			}
		}

		
		public void csvToXLSX(String csvFileAddress, String xlsxFileAddress) {
		    try {
		        XSSFWorkbook workBook = new XSSFWorkbook();
		        XSSFSheet sheet = workBook.createSheet("book1");
		        String currentLine=null;
		        int RowNum=0;
		        BufferedReader br = new BufferedReader(new FileReader(csvFileAddress));
		        while ((currentLine = br.readLine()) != null) {
		            String str[] = currentLine.split(",");
		            RowNum++;
		            XSSFRow currentRow=sheet.createRow(RowNum);
		            for(int i=0;i<str.length;i++){
		                currentRow.createCell(i).setCellValue(str[i]);
		            }
		        }
		        FileOutputStream fileOutputStream =  new FileOutputStream(xlsxFileAddress);
		        workBook.write(fileOutputStream);
		        fileOutputStream.close();
		        
		        System.out.println("--- csvToXLSX Done -----");

		    } catch (Exception ex) {
		        System.out.println(ex.getMessage()+"Exception in try");
		    }
		}

}
