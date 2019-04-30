package com.example.login.test;

public class SubmissionDisplay {
    private String name;
    private String id;
    private String content;

    public SubmissionDisplay() {
        name = "";
        id = "";
        content = "";
    }

    public SubmissionDisplay(String name, String id, String content) {
        this.name = name;
        this.id = id;
        this.content = content;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
