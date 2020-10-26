package com.tjrac.studentAffairs.web.ajax;

import com.tjrac.studentAffairs.utils.MD5;
import com.tjrac.studentAffairs.web.BaseUpload;
import org.apache.poi.util.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Collection;

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
            String md5 = MD5.MD5file(IOUtils.toByteArray(file.getInputStream()));
            file.write(savePath + "/" + md5 + ".xlsx");
        }
    }
}
