package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.service.ExcelService;
import com.tjrac.studentAffairs.service.impl.ExcelServiceImpl;
import com.tjrac.studentAffairs.web.BaseUpload;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;

/**
 * UploadExcel
 *
 * @author : xziying
 * @create : 2020-10-26 22:05
 */
@WebServlet("/UploadExcel")
@MultipartConfig
public class UploadExcel extends BaseUpload {

    public void importStudentInfo(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        Part file = req.getPart("file");
        String savePath = req.getServletContext().getRealPath("/temp/excel");
        System.out.println(savePath);
        if (file != null){
            ExcelService excelService = new ExcelServiceImpl();
            resp.getWriter().write(excelService.importInfo(req.getSession(), file.getInputStream()));
        }
    }
}
