package com.example.login;

public class RoomManager {
    String name;
    String password;

    RoomManager() {
        name = "";
        password = "";
    }

    RoomManager(String name, String password) {
        this.name = name;
        this.password = password;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword() {
        return this.password;
    }
}
