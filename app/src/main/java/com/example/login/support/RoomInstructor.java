package com.example.login.support;

public class RoomInstructor {
    private String name;

    public RoomInstructor() {
        name = "";
    }

    public RoomInstructor(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
