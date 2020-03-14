package com.alls.myapplication;

class Student {

public Student(String id, String num, String log, String add, String type, String uri, String user, String currentDate, String mobilno)
{
    this.num = num;
    this.id = id;
  this.log=log;
  this.address=add;
  this.type=type;
  this.profileurl=uri;
  this.name=user;
  this.date=currentDate;
  this.mobileno=mobilno;
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
    String address;
    String name;

    public String getMobileno() {
        return mobileno;
    }

    public void setMobileno(String mobileno) {
        this.mobileno = mobileno;
    }

    String mobileno;

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    String date;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getProfileurl() {
        return profileurl;
    }

    public void setProfileurl(String profileurl) {
        this.profileurl = profileurl;
    }

    private  String profileurl;
    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    String type;

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


   }
