package com.tjrac.studentAffairs.utils.excel;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Student;
import com.tjrac.studentAffairs.utils.MD5;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.FileInputStream;
import java.io.IOException;
import java.lang.reflect.Field;
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

    public static void importExcel(String fileName) throws IOException, NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //获取Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook(new FileInputStream(fileName));
        //获取sheet
        XSSFSheet sheet = workbook.getSheet("sheet0");

        //Dao层
        BaseDao<Student> studentBaseDao = new BaseDao<>(Student.class);

        Field[] declaredFields = Student.class.getDeclaredFields(); //获取Student所有的属性
        List<Field> fields = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals("student_id") || declaredField.getName().equals("password") || declaredField.getName().equals("competence_id")) {
                continue;
            }
            fields.add(declaredField);
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

                    String setMethodName = "set" + fields.get(j).getName().substring(0, 1).toUpperCase() + fields.get(j).getName().substring(1);

                    System.out.println(cell.toString());

                    Method method = Student.class.getMethod(setMethodName, String.class);
                    method.invoke(student, cell.toString());

                }

                int insert = studentBaseDao.insert(student);

                List<Student> failureInfo = new ArrayList<>(); //存放失败信息

                if (insert == -1) { //导出失败
                    failureInfo.add(student); //将其存放起来
                }

            }

        }

    }


    @Test
    public void test() throws InvocationTargetException, NoSuchMethodException, IllegalAccessException, IOException {

        importExcel("temp\\excel.xlsx");

    }

}
