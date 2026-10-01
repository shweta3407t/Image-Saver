package com.example.imageSaver.dto;


public class RegisterRequestDTO {


    private  String name;

    private  String password;

    public String getUserName() {

        return name;
    }

    public void setUserName(String userName) {


        this.name = userName;
    }

    public String getPassword() {

        return password;
    }

    public void setPassword(String password) {

        this.password = password;
    }
}
