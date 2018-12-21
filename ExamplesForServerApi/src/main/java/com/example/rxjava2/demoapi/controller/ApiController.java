package com.example.rxjava2.demoapi.controller;

import com.example.rxjava2.demoapi.domain.Result;
import com.example.rxjava2.demoapi.domain.Student;
import com.example.rxjava2.demoapi.util.ResultUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class ApiController {

    private static final Logger sLogger = LoggerFactory.getLogger(ApiController.class);


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



}
