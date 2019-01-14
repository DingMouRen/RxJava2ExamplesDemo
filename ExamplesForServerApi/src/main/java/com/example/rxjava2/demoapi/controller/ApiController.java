package com.example.rxjava2.demoapi.controller;

import com.example.rxjava2.demoapi.domain.*;
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
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@RestController
public class ApiController {

    private static final Logger sLogger = LoggerFactory.getLogger(ApiController.class);


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

    /**
     * 轮询操作
     * @return
     */
    @GetMapping(value = "/polling")
    public Result<String> polling(){
        String time = new Date().toString();
        return ResultUtil.success("返回响应时间："+time);
    }

    /**
     * 基于错误的重试操作
     * @return
     */
    @GetMapping(value = "/retry")
    public Result<String> retry(){
       return ResultUtil.success("请求成功");
    }

    /**
     * 优先加载缓存，同时发起请求
     * @return
     */
    @GetMapping(value = "/net/data")
    public Result<List<NetData>> getNetData(@RequestParam("delayTime") Long delayTime) throws InterruptedException {

        Thread.sleep(delayTime);

        List<NetData> list = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            NetData netData = new NetData("来自网络","数据项 --- "+i);
            list.add(netData);
        }
        return ResultUtil.success(list);
    }

    /**
     * 嵌套网络请求
     * 获取第二道门得密码
     */
    @GetMapping(value = "nest/1")
    public Result<Nest1Bean> requestNest1(@RequestParam("pwd") int firstDoorPwd){
        Nest1Bean bean = null;
        if (firstDoorPwd == 1234){
            bean =  new Nest1Bean("获取到第二道门得密码",1234);
        }else {
            bean =  new Nest1Bean("没有获取到第二道门得密码",0);
        }
        return ResultUtil.success(bean);
    }

    /**
     * 嵌套网络请求
     * 打开第二道门
     */
    @GetMapping(value = "nest/2")
    public Result<Nest2Bean> requestNest2(@RequestParam("pwd") int secondDoorPwd){
        Nest2Bean bean = null;
        if (secondDoorPwd == 1234){
            bean = new Nest2Bean("打开第二道门","第二道门被打开，好多宝藏");
        }else {
            bean = new Nest2Bean("没有打开第二道门","小伙子，宝藏正在离你而去。。。。");
        }
        return ResultUtil.success(bean);
    }

    /**
     * 获取指定年级得老师
     */
    @GetMapping(value = "/teacher")
    public Result<Teacher> getTeacher(@RequestParam("grade") int grade){
        Teacher teacher = new Teacher(grade+"年级老师:Json-"+grade,grade+"");
        return ResultUtil.success(teacher);
    }

    /**
     * 获取指定年级得学生
     */
    @GetMapping(value = "/students")
    public Result<List<Student>> getStudents(@RequestParam("grade") int grade){
        List<Student> studentList = new ArrayList<>();
        String[] studentNames = new String[]{"小红","小明","小黑","小白","拉拉"};
        for (int i = 0; i < studentNames.length; i++) {
            Student student = new Student(grade+"年级学生:"+studentNames[i],grade+"",i%2 == 1 ?"男":"女");
            studentList.add(student);
        }
        return ResultUtil.success(studentList);
    }

}
