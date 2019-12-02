package com.example.myapplication;

class Student {

public Student(String id, String num, String log)
{
    this.num = num;
    this.id = id;
  this.log=log;

}

    public Student() {

    }

    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    String num;
    String id;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getLog() {
        return log;
    }

    public void setLog(String log) {
        this.log = log;
    }

    String log;

    public int getC() {
        return c;
    }

    public void setC(int c) {
        this.c = c;
    }

    int c;


   }
