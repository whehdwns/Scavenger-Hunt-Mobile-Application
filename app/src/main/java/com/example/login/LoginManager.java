package com.example.login;

public class LoginManager {
     String role;
     String name;
     String email;
     String ID;

    public LoginManager(String role, String name, String email, String ID) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.ID = ID;
    }

    public String getRole() {
        return this.role;
    }
}
