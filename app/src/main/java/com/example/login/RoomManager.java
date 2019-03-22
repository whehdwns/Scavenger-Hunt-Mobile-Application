package com.example.login;

public class RoomManager {
    private String instructor;
    private String name;
    private String number;
    private String password;

    public RoomManager() {
        instructor = "";
        name = "";
        number = "";
        password = "";
    }

    public RoomManager(String instructor, String name, String number, String password) {
        this.instructor = instructor;
        this.name = name;
        this.number = number;
        this.password = password;
    }


    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getPassword() {
        return this.password;
    }

    public void setPassword(String password) {
        this.password = password;
    }
}
