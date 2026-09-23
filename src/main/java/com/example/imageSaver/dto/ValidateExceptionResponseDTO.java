package com.example.imageSaver.dto;

import java.time.LocalDateTime;
import java.util.Map;

public class ValidateExceptionResponseDTO {


    private  LocalDateTime timeStamp;
    private int statusCode;
    private  String error;
    private  String  message;
    private String  path;

    private  Map<String ,String> fieldError;


    public ValidateExceptionResponseDTO(LocalDateTime timeStamp, int statusCode, String error, String message, String path, Map<String, String> fieldError) {
        this.timeStamp = timeStamp;
        this.statusCode = statusCode;
        this.error = error;
        this.message = message;
        this.path = path;
        this.fieldError = fieldError;
    }

    public LocalDateTime getTimeStamp() {
        return timeStamp;
    }

    public void setTimeStamp(LocalDateTime timeStamp) {
        this.timeStamp = timeStamp;
    }



    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public int getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(int statusCode) {
        this.statusCode = statusCode;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public Map<String, String> getFieldError() {
        return fieldError;
    }

    public void setFieldError(Map<String, String> fieldError) {
        this.fieldError = fieldError;
    }
}
