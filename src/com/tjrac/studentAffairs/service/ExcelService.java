package com.tjrac.studentAffairs.service;

import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.InputStream;

/**
 * ExcelService
 *
 * @author : xziying
 * @create : 2020-10-22 10:47
 */
public interface ExcelService {
    /**
     * 导出模板信息
     * @param fileName 目录名
     * @return json数据
     * event -> 0 成功 1 失败
     */
    String exportModel(String fileName);

    void exportInfo(HttpSession session, HttpServletResponse resp);

    /**
     * 导入学生数据
     * @param session
     * @param inputStream 导入文件流
     */
    String importInfo(HttpSession session, InputStream inputStream);

    /**
     * 导出 导入失败的学生
     * @param session
     * @param resp
     */
    void getFailureStream(HttpSession session, HttpServletResponse resp);
}
