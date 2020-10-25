package com.tjrac.studentAffairs.utils.excel;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.admin.Export;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.utils.MD5;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 导入数据
 *
 * @author ZeNing
 * @create 2020-10-17 21:28
 */
public class ImportExcel {

    public static boolean importExcel(String fileName, OutputStream outputStream) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException, NoSuchFieldException {

        //获取Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileName));
        //获取sheet
        XSSFSheet sheet = workbook.getSheet("sheet0");


        //获取表内信息
        BaseDao<Student> studentBaseDao = new BaseDao<>(Student.class);
        BaseDao<Export> exportBaseDao = new BaseDao<>(Export.class);
        List<Export> exports = exportBaseDao.queryList();

        List<String> studentFieldName = new ArrayList<>(); //获取的属性名
        List<Student> failureInfo = new ArrayList<>(); //存放失败信息

        //获取export的所有属性
        Method getKeyName = Export.class.getMethod("getKeyName");

        for (int i = 0; i < exports.size(); i++) {
            Object invoke = getKeyName.invoke(exports.get(i));
            studentFieldName.add(invoke + "");
        }

        for (int i = 1; i <= sheet.getLastRowNum(); i++) { //遍历每一行

            //获取每一行
            XSSFRow row = sheet.getRow(i);

            if (row != null) { //如果excel不是空表

                Student student = new Student(); //创建一个student对象

                String password = MD5.MD5Encrypt("123456");
                student.setPassword(password); //默认密码为123456
                student.setCompetence(4); //默认权限为4

                for (int j = 0; j < row.getPhysicalNumberOfCells(); j++) { //遍历每一个单元格

                    XSSFCell cell = row.getCell(j); //获取每一个单元格

                    String setMethodName = "set" + studentFieldName.get(j).substring(0, 1).toUpperCase() + studentFieldName.get(j).substring(1);
                    System.out.println(setMethodName);

                    if (setMethodName.equals("setPassword")) {
                        Method method = Student.class.getMethod(setMethodName, String.class);
                        method.invoke(student, MD5.MD5Encrypt(cell.toString()));
                        continue;
                    }

                    System.out.println(cell.toString());

                    Method method = Student.class.getMethod(setMethodName, String.class);
                    method.invoke(student, cell.toString());

                }

                if (studentBaseDao.insert(student) == 0) { //导出成功
                    if (i != sheet.getLastRowNum()) {
                        sheet.shiftRows(i, sheet.getLastRowNum(), -1);
                    } else {
                        sheet.removeRow(row);
                    }
                }

            }

        }

        workbook.write(new FileOutputStream(fileName));

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
    public void test() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException, NoSuchFieldException {

        importExcel("temp\\model1.xlsx", null);

    }

}
