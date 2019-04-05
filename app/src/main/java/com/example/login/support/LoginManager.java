package com.example.login.support;

public class LoginManager {
    private String role;
    private String name;
    private String email;
    private String id;

    public LoginManager() {
        role = "";
        name = "";
        email = "";
        id = "";
    }

    public LoginManager(String role, String name, String email, String id) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.id = id;
    }

    public String getRole() {
        return this.role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getName() {
        return this.name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return this.email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getId() {
        return this.id;
    }

    public void setId(String ID) {
        this.id = id;
    }
}
