package com.tjrac.studentAffairs.utils.excel;

import com.tjrac.studentAffairs.dao.BaseDao;
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
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出学生信息表为Excel
 *
 * @author ZeNing
 * @create 2020-10-18 14:52
 */
public class ExportStudentInfo {

    public static boolean export(OutputStream outputStream) throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //创建Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建一个工作表sheet
        Sheet sheet = workbook.createSheet();

        XSSFCellStyle css = workbook.createCellStyle(); //设置单元格格式
        DataFormat format = workbook.createDataFormat();
        css.setDataFormat(format.getFormat("@"));

        //获取表内信息
        BaseDao<Student> studentBaseDao = new BaseDao<>(Student.class);
        List<Student> students = studentBaseDao.queryList();

        //获取student的所有属性
        Field[] declaredFields = Student.class.getDeclaredFields();
        List<Method> getMethodNames = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals("student_id") || declaredField.getName().equals("password") || declaredField.getName().equals("competence_id")) {
                continue;
            }

            //获取所有属性的属性名
            String fieldName = declaredField.getName();

            //获取每个属性的get方法
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = Student.class.getMethod(getMethodName);//获取方法
            getMethodNames.add(method);
        }

        for (int i = 0; i < students.size(); i++) { //遍历每一行数据


            //创建第i行
            Row row = sheet.createRow(i);

            //行内单元格
            Cell cell;

            for (int j = 0; j < getMethodNames.size(); j++) { //遍历每一列
                sheet.setColumnWidth(j, 20 * 256);

                String invoke = (String) getMethodNames.get(j).invoke(students.get(i));

                cell = row.createCell(j); //创建第i个单元格
                cell.setCellStyle(css); //设置格式
                cell.setCellValue(invoke);
            }

        }


        try {
            // 存放到浏览器文件流
            workbook.write(outputStream);
            outputStream.flush();
            return true;

        } catch (IOException e) {
            e.printStackTrace();
            return false;
        }


    }

    @Test
    public void test() throws NoSuchMethodException, IllegalAccessException, InvocationTargetException {
    }

}
