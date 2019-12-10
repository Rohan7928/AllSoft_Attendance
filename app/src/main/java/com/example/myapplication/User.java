package com.example.myapplication;

import java.util.List;

public class User {
    public String getNum() {
        return num;
    }

    public void setNum(String num) {
        this.num = num;
    }



    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }



    public String getTime() {
        return time;
    }

    public void setTime(String time) {
        this.time = time;
    }


    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }


    public String getDesignation() {
        return designation;
    }

    public void setDesignation(String designation) {
        this.designation = designation;
    }
    String name;
    String time;
    String email;
    String designation;
    String num;

    public User(String num, String user, String time, String email, String designation)
    {
        this.num=num;
        this.name=user;
        this.time=time;
        this.email=email;
        this.designation=designation;
    }
    public  User()
    {

    }
}
