package com.example.login;

public class RoomManager {
    String name;
    String number;
    String password;

    RoomManager() {
        name = "";
        number = "";
        password = "";
    }

    RoomManager(String name, String number, String password) {
        this.name = name;
        this.number = number;
        this.password = password;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setNumber(String number) {
        this.number = number;
    }

    String getNumber() {
        return this.number;
    }

    void setPassword(String password) {
        this.password = password;
    }

    String getPassword() {
        return this.password;
    }
}
