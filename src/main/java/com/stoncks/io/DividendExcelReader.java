package com.stoncks.io;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Iterator;

public class DividendExcelReader {

    //private String file = "/home/mx/IdeaProjects/stoncks/transactions";



    public ArrayList<String[]> getTableAsArray(String filePath){

        String cellVal;
        String line;
        ArrayList<String[]> rows = new ArrayList<>();

        try {
            FileInputStream excelFile = new FileInputStream(new File(filePath));
            Workbook workbook = new HSSFWorkbook(excelFile);
            Sheet datatypeSheet = workbook.getSheetAt(0);
            Iterator<Row> rowIterator = datatypeSheet.iterator();

            while (rowIterator.hasNext()) {
                cellVal = "";
                line = "";

                Row currentRow = rowIterator.next();
                Iterator<Cell> cellIterator = currentRow.iterator();

                while (cellIterator.hasNext()) {
                    Cell currentCell = cellIterator.next();

                        switch (currentCell.getCellType()) {
                            case BLANK:
                                cellVal = "--#";
                                break;
                            case STRING:
                                cellVal = currentCell.getStringCellValue() + "#";
                                break;
                            case NUMERIC:
                                cellVal = String.valueOf(currentCell.getNumericCellValue()) + "#";
                                break;
                            default:
                                cellVal = " UNKNOWN";
                                break;
                        }


                    line += cellVal + " ";
                }
                String[] row = line.split("#");
                rows.add(row);
            }
        } catch (IOException e) {
            e.printStackTrace();
        }
        return rows;
    }





    }
