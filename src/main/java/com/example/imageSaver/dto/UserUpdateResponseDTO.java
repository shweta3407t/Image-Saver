package com.example.imageSaver.dto;

public class UserUpdateResponseDTO {
    private String userName;
    private String massage;

    public UserUpdateResponseDTO(){}

    public UserUpdateResponseDTO(String userName, String massage) {
        this.userName = userName;
        this.massage = massage;
    }

    public String getUserName() {
        return userName;
    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public String getMassage() {
        return massage;
    }

    public void setMassage(String massage) {
        this.massage = massage;
    }
}
