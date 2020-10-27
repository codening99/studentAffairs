package com.tjrac.studentAffairs.service.impl;

import com.tjrac.studentAffairs.service.ExcelService;
import com.tjrac.studentAffairs.utils.JsonPack;
import com.tjrac.studentAffairs.utils.excel.ExportModelExcel;
import com.tjrac.studentAffairs.utils.excel.ExportStudentInfo;
import com.tjrac.studentAffairs.utils.excel.ImportExcel;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.nio.charset.StandardCharsets;

/**
 * ExcelServiceImpl
 *
 * @author : xziying
 * @create : 2020-10-22 10:47
 */
public class ExcelServiceImpl implements ExcelService {
    @Override
    public String exportModel(String fileName) {
        JsonPack jsonPack = new JsonPack();
        // 验证权限

        //文件导出
        String url = "excel\\modelExcel.xlsx";
        try {
            boolean export = ExportModelExcel.export(fileName + url);
            if (export) {
                jsonPack.put("event", 0);
                jsonPack.put("url", ".\\" + url);
                return jsonPack.toJson();
            }
            jsonPack.put("event", 1);
            jsonPack.put("msg", "导出模板失败！");
            return jsonPack.toJson();
        } catch (NoSuchMethodException | InvocationTargetException | IllegalAccessException e) {
            e.printStackTrace();
            jsonPack.put("event", 1);
            jsonPack.put("msg", "导出模板失败！");
            return jsonPack.toJson();
        }
    }

    @Override
    public void exportInfo(HttpSession session, HttpServletResponse resp) {
        // 判断权限

        // 导出信息
        resp.setContentType("application/x-download");
        try {
            resp.addHeader("Content-Disposition", "attachment;filename=" +
                    new String("学生信息".getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xlsx");
            ExportStudentInfo.export(resp.getOutputStream());
            resp.flushBuffer();
        } catch (NoSuchMethodException | IllegalAccessException | InvocationTargetException | IOException e) {
            e.printStackTrace();
            try {
                resp.flushBuffer();
            } catch (IOException ioException) {
                ioException.printStackTrace();
            }
        }
    }

    @Override
    public String importInfo(HttpSession session, InputStream inputStream) {
        JsonPack jsonPack = new JsonPack();

        try {
            int count = ImportExcel.importExcel(session, inputStream);
            if (count > 0) {
                jsonPack.put("event", 3);
                jsonPack.put("msg", "有导入失败的学生信息");

            } else {
                jsonPack.put("event", 0);
                jsonPack.put("msg", "导入完全成功");
            }
        } catch (Exception e) {
            e.printStackTrace();
            System.out.println("导入文件失败");
            jsonPack.put("event", 4);
            jsonPack.put("msg", "未知错误，导入失败");
        }

        return jsonPack.toJson();
    }

    @Override
    public void getFailureStream(HttpSession session, HttpServletResponse resp) {
        resp.setContentType("application/x-download");
        try {
            resp.addHeader("Content-Disposition", "attachment;filename=" +
                    new String("failureStream".getBytes(StandardCharsets.UTF_8), "ISO8859-1") + ".xlsx");
            Object attribute = session.getAttribute("failureStream");
            if (attribute != null) {
                resp.getOutputStream().write(((ByteArrayOutputStream) attribute).toByteArray());
                resp.getOutputStream().flush();
            }

        } catch (IOException e) {
            e.printStackTrace();
        }

    }
}
