package com.tjrac.studentAffairs.utils.excel;

import com.tjrac.studentAffairs.dao.BaseDao;
import com.tjrac.studentAffairs.domain.user.Export;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;

/**
 * 导出模板
 *
 * @author ZeNing
 * @create 2020-10-17 15:42
 */
public class exportModelExcel {

    public static void export() throws NoSuchMethodException, InvocationTargetException, IllegalAccessException {

        //创建Excel工作簿
        XSSFWorkbook workbook = new XSSFWorkbook();

        //创建一个工作表sheet
        Sheet sheet = workbook.createSheet();

        //获取表内信息
        BaseDao<Export> exportBaseDao = new BaseDao<>(Export.class);
        List<Export> exports = exportBaseDao.queryList();

        //获取export的所有属性
        Field[] declaredFields = Export.class.getDeclaredFields();
        List<Field> fields = new ArrayList<>();

        for (Field declaredField : declaredFields) {
            if (declaredField.getName().equals("excel_id") || declaredField.getName().equals("keyName")) {
                continue;
            }
            fields.add(declaredField);
        }

        for (int i = 0; i < fields.size(); i++) {
            //获取所有属性的属性名
            String fieldName = fields.get(i).getName();

            //获取每个属性的get方法
            String getMethodName = "get" + fieldName.substring(0, 1).toUpperCase() + fieldName.substring(1);
            Method method = Export.class.getMethod(getMethodName);//获取方法

            if (getMethodName.equals("getExcel_id") || getMethodName.equals("getKeyName")) {
                continue;
            }

            //获取表中列数 创建string数组
            String[] title = new String[exports.size()];

            for (int j = 0; j < exports.size(); j++) {
                sheet.setColumnWidth(j, 20 * 256);
                Object invoke = method.invoke(exports.get(j)); //每个对象执行一次方法
                title[j] = "" + invoke;
            }

            //创建第i行
            Row row = sheet.createRow(i);
            //行内单元格
            Cell cell;

            //插入第i行数据
            for (int j = 0; j < title.length; j++) {
                cell = row.createCell(j); //创建单元格
                cell.setCellValue(title[j]); //给单元格填入值
            }
        }

        //创建一个文件 存放
        File file = new File("temp/excel.xlsx");

        try {

            FileOutputStream output = new FileOutputStream(file);
            workbook.write(output);
            output.flush();
            output.close();

        } catch (IOException e) {
            e.printStackTrace();
        }

    }


}
