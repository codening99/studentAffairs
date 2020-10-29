package com.tjrac.studentAffairs.web;

import org.apache.poi.util.IOUtils;

import javax.servlet.ServletException;
import javax.servlet.annotation.MultipartConfig;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.Part;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.Method;

/**
 * UploadBase 上传文件基类
 *
 * @author : xziying
 * @create : 2020-10-26 22:22
 */
@MultipartConfig
public class BaseUpload extends HttpServlet {
    @Override
    protected void service(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        req.setCharacterEncoding("utf-8");
        resp.setCharacterEncoding("utf-8");
        resp.setContentType("text/html;charset=utf-8");
        //String savePath = req.getServletContext().getRealPath("/myFiles");
        Part action = req.getPart("action");
        if (action != null){
            InputStream inputStream = action.getInputStream();
            String actionStr = new String(IOUtils.toByteArray(inputStream));
            try {
                // 获取action业务鉴别字符串，获取相应的业务 方法反射对象
                Method method = this.getClass().getDeclaredMethod(actionStr, HttpServletRequest.class, HttpServletResponse.class);
                // 调用目标业务 方法
                method.invoke(this, req, resp);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }


    }

}
