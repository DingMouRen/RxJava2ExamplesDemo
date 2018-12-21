package com.example.rxjava2.demoapi.domain;


public class Student {
    private String name;
    private String age;
    private SchoolBag schoolBag;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAge() {
        return age;
    }

    public void setAge(String age) {
        this.age = age;
    }

    public SchoolBag getSchoolBag() {
        return schoolBag;
    }

    public void setSchoolBag(SchoolBag schoolBag) {
        this.schoolBag = schoolBag;
    }

    @Override
    public String toString() {
        return "Student{" +
                "name='" + name + '\'' +
                ", age='" + age + '\'' +
                ", schoolBag=" + schoolBag +
                '}';
    }

    public static class SchoolBag{
        private String bagName;
        private String bagPrice;

        public String getBagName() {
            return bagName;
        }

        public void setBagName(String bagName) {
            this.bagName = bagName;
        }

        public String getBagPrice() {
            return bagPrice;
        }

        public void setBagPrice(String bagPrice) {
            this.bagPrice = bagPrice;
        }

        @Override
        public String toString() {
            return "SchoolBag{" +
                    "bagName='" + bagName + '\'' +
                    ", bagPrice='" + bagPrice + '\'' +
                    '}';
        }
    }
}
