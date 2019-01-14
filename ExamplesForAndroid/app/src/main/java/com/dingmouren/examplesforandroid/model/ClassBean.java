package com.dingmouren.examplesforandroid.model;

import java.util.List;

public class ClassBean {
    public String teacherName;
    public String grade;
    public List<Student> studentList;

    public ClassBean(String teacherName, String grade, List<Student> studentList) {
        this.teacherName = teacherName;
        this.grade = grade;
        this.studentList = studentList;
    }
}
