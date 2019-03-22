package com.example.login;

public class LoginManager {
    String role;
    String name;
    String email;
    String id;

    LoginManager() {
        role = "";
        name = "";
        email = "";
        id = "";
    }

    LoginManager(String role, String name, String email, String id) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    void setRole(String role) {
        this.role = role;
    }

    String getRole() {
        return this.role;
    }

    void setName(String name) {
        this.name = name;
    }

    String getName() {
        return this.name;
    }

    void setEmail(String email) {
        this.email = email;
    }

    String getEmail() {
        return this.email;
    }

    void setId(String ID) {
        this.id = id;
    }

    String getId() {
        return this.id;
    }
}
