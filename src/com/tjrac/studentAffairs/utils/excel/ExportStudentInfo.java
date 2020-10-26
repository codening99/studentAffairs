package com.tjrac.studentAffairs.utils.excel;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.admin.Export;
import com.tjrac.studentAffairs.domain.user.Student;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.DataFormat;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFCellStyle;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.List;

/**
 * 导出学生信息表为Excel
 *
 * @author ZeNing
 * @create 2020-10-18 14:52
 */
public class ExportStudentInfo {
    // 首字母大写
    private static String captureName(String name) {
        char[] cs = name.toCharArray();
        cs[0] -= 32;
        return String.valueOf(cs);
    }

    public static boolean export(OutputStream outputStream) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //创建Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建一个工作表sheet
        Sheet sheet = workbook.createSheet();
        sheet.setVerticallyCenter(true);

        XSSFCellStyle css = workbook.createCellStyle(); //设置单元格格式
        DataFormat format = workbook.createDataFormat();
        css.setDataFormat(format.getFormat("@"));

        //获取表内信息
        BaseDao<Student> studentBaseDao = new BaseDao<>(Student.class);
        BaseDao<Export> exportBaseDao = new BaseDao<>(Export.class);
        List<Export> exports = exportBaseDao.queryList();
        List<Student> students = studentBaseDao.queryList();

        int iterator = 0;

        // 写出标题
        Row title = sheet.createRow(iterator++);
        for (int i = 0; i < exports.size(); i++) {
            sheet.setColumnWidth(i, 20 * 256);
            //行内单元格
            Cell cell;
            String invoke = exports.get(i).getColumnName();
            cell = title.createCell(i); //创建第i个单元格
            cell.setCellStyle(css); //设置格式
            cell.setCellValue(invoke);
        }
        for (Student student : students){
            Row row = sheet.createRow(iterator++);
            for (int i = 0; i < exports.size(); i++) {
                Cell cell;
                Method method = Student.class.getDeclaredMethod(
                        "get" + captureName(exports.get(i).getKeyName()));
                String invoke = "";
                try {
                    invoke = String.valueOf(method.invoke(student));
                } catch (Exception e){
                    System.out.println("执行方法失败！");
                }
                cell = row.createCell(i); //创建第i个单元格
                cell.setCellStyle(css); //设置格式
                cell.setCellValue(invoke);
            }
        }

        //创建一个文件 存放
        File file = new File("temp/model1.xlsx");
        try {

            FileOutputStream output = new FileOutputStream(file);
            workbook.write(output);
            output.flush();
            output.close();
            return true;
        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }

//        try {
//            // 存放到浏览器文件流
//            workbook.write(outputStream);
//            outputStream.flush();
//            return true;
//
//        } catch (IOException e) {
//            e.printStackTrace();
//            return false;
//        }
    }

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
        export(null);
    }

}
