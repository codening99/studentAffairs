package com.tjrac.studentAffairs.utils.excel;

import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.FileInputStream;
import java.io.IOException;

/**
 * 导入数据
 *
 * @author ZeNing
 * @create 2020-10-17 21:28
 */
public class importExcel {

    public static void main(String[] args) throws IOException {

        //获取Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream("temp/excel.xlsx"));
        //获取sheet
        XSSFSheet sheet = workbook.getSheet("sheet0");
//
//        for (int i = 0; i <= sheet.getLastRowNum(); i++) { //遍历每一行
//
//        }

    }


}
