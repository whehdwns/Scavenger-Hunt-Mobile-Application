package com.example.login.support;

public class RoomDisplay {
    private String instructor;
    private String name;
    private String number;

    public RoomDisplay() {
        instructor = "";
        name = "";
        number = "";
    }

    public RoomDisplay(String instructor, String name, String number) {
        this.instructor = instructor;
        this.name = name;
        this.number = number;
    }

    public String getInstructor() {
        return instructor;
    }

    public void setInstructor(String instructor) {
        this.instructor = instructor;
    }

    public String getName() {
        return name;
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
}
