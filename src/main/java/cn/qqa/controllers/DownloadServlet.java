package cn.qqa.controllers;


import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.net.URLEncoder;

@WebServlet
public class DownloadServlet extends HttpServlet {
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        //获得当前项目路径下的下载文件（真实开发中文件名肯定是从数据中读取的）
        String realPath = this.getServletContext().getRealPath("/file/1.jpg");
        //根据文件路径封装成File对象，方便获取文件名
        File tmpFile = new File(realPath);
        //可以根据File对象获得文件名
        String fileName = tmpFile.getName();
        System.out.println(fileName);
        //设置响应头 content-disposition:就是设置文件下载的打开方式，默认在网页打开，直接显示在网页上
        //设置attachment:filename=（附件）就是为了以下载方式来打开文件
        //UTF-8：防止中文乱码
        resp.setHeader("content-disposition","attachment:filename=" + URLEncoder.encode(fileName,"UTF-8"));
        //根据文件路径，将文件封装成文件输入流
        FileInputStream fileInputStream = new FileInputStream(realPath);
        int len = 0;
        //声明一个1KB的缓冲区
        byte[] buffer = new byte[1024];
        //获取输出流
        OutputStream outputStream = resp.getOutputStream();
        //循环读取文件，每次读1KB,避免内存溢出
        while ((len=fileInputStream.read(buffer))>0){
            //往客户端写入，将缓冲区中的数据输出到客户端浏览器
            outputStream.write(buffer,0,len);
        }
        fileInputStream.close();
    }
}
