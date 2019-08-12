package com.codeelit.ts.model;

public class Student {

    public String Username, Email, College;

    public Student(String inputName, String inputEmail, String inputCollege, String inputImage) {
    }

    public Student(String username, String email, String college) {
        Username = username;
        Email = email;
        College = college;
    }
}
