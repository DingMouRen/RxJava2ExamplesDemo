package com.example.rxjava2.demoapi.controller;

import com.example.rxjava2.demoapi.domain.Result;
import com.example.rxjava2.demoapi.domain.Student;
import com.example.rxjava2.demoapi.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.util.ClassUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.util.Date;

@RestController
public class ApiController {

    private static final Logger sLogger = LoggerFactory.getLogger(ApiController.class);

    /**
     * 返回学生信息
     * @return
     */
    @GetMapping(value = "/student_info")
    public Result<Student> getStudent(){
        Student student = new Student();
        student.setName("小明");
        student.setAge("18");
        Student.SchoolBag schoolBag = new Student.SchoolBag();
        schoolBag.setBagName("大书包");
        schoolBag.setBagPrice("$99");
        student.setSchoolBag(schoolBag);
        return ResultUtil.success(student);
    }

    /**
     * 下载图片
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/file/download/img")
    public ResponseEntity<byte[]> downloadImg() throws IOException {

        String filePath = "src/main/resources/static/img/img_1.jpg";
        @SuppressWarnings("resource")
        InputStream in = new FileInputStream(new File(filePath));// 将该文件加入到输入流之中
        byte[] body = null;
        body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
        in.read(body);// 读入到输入流里面

        String fileName = new String("img.jpg".getBytes("gbk"), "iso8859-1");// 防止中文乱码
        HttpHeaders headers = new HttpHeaders();// 设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }

    /**
     * 下载apk
     * @return
     * @throws IOException
     */
    @GetMapping(value = "/file/download/apk")
    public ResponseEntity<byte[]> downloadApk() throws IOException {

        String filePath = "src/main/resources/static/apk/english.apk";
        @SuppressWarnings("resource")
        InputStream in = new FileInputStream(new File(filePath));// 将该文件加入到输入流之中
        byte[] body = null;
        body = new byte[in.available()];// 返回下一次对此输入流调用的方法可以不受阻塞地从此输入流读取（或跳过）的估计剩余字节数
        in.read(body);// 读入到输入流里面

        String fileName = new String("english.apk".getBytes("gbk"), "iso8859-1");// 防止中文乱码
        HttpHeaders headers = new HttpHeaders();// 设置响应头
        headers.add("Content-Disposition", "attachment;filename=" + fileName);
        HttpStatus statusCode = HttpStatus.OK;// 设置响应吗
        ResponseEntity<byte[]> response = new ResponseEntity<byte[]>(body, headers, statusCode);
        return response;
    }

    /**
     * 优化搜索功能的例子api
     * @return
     */
    @GetMapping(value = "/search")
    public Result<String> search(@RequestParam String query){
        return ResultUtil.success(query);
    }

    @GetMapping(value = "/polling")
    public Result<String> polling(){
        String time = new Date().toString();
        return ResultUtil.success("返回响应时间："+time);
    }

}
