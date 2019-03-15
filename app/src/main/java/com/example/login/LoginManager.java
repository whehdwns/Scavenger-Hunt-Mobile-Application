package com.example.login;

class LoginManager {
    private final String role;
    private final String name;
    private final String email;
    private final String ID;

    LoginManager(String role, String name, String email, String ID) {
        this.role = role;
        this.name = name;
        this.email = email;
        this.ID = ID;
    }

    public String getRole() {
        return this.role;
    }
}
