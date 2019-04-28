package com.example.login.support;

public class TaskSubmission {
    public String content;
    public String user;

    public TaskSubmission () {
        content = "";
        user = "";
    }

    public TaskSubmission(String content, String user) {
        this.content = content;
        this.user = user;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getUser() {
        return user;
    }

    public void setUser(String user) {
        this.user = user;
    }
}
