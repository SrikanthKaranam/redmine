package com.creatingmuiltipleredminetickets;

import java.io.FileInputStream;

import jxl.Sheet;
import jxl.Workbook;

public class DataProvider {
  public static void main(String[] args) throws Exception {

    // //get workbook in the excel file and refer in wb object

    Workbook wb = Workbook.getWorkbook(new FileInputStream(
        "C:\\Users\\CFLAP471\\Desktop\\CreatingMuiltipleRedmineTickets.xls"));

    // //////get sheet 0 in the workbook
    Sheet ws = wb.getSheet(0);

    // //////read data in the cells in column,row
    for (int r = 1; r < ws.getRows(); r++) {
      for (int c = 0; c < ws.getColumns(); c++) {
        System.out.print("[" + ws.getCell(c, r).getContents() + "]");
      }
      System.out.println("");
      System.out.println("=================");
    }
  }
}
