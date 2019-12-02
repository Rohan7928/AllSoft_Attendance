package com.example.myapplication;

public class User {
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }

    String num;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    String name;

    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }

    String time;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }

    String email;
    String code;
    String designation;


    public User(String num, String user, String time, String email, String code, String designation)
    {
        this.num=num;
        this.name=user;
        this.time=time;
        this.email=email;
        this.code=code;
        this.designation=designation;
    }
    public  User()
    {

    }
}
