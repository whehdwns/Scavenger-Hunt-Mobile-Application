package com.example.login.support;

public class RoomJoined {
    private String name;

    public RoomJoined() {
        name = "";
    }

    public RoomJoined(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
