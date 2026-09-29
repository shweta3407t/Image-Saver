package com.example.imageSaver.exception;


 public class IncorrectCredentialsException  extends  RuntimeException{
     @Override
     public String getMessage() {
         return "Incorrect Credentials";
     }
}
