package com.example.login.support;

public class SubmissionManager {
    private String content;
    private String grade;
    private String id;
    private String name;
    private String description;
    private String uid;

    public SubmissionManager() {
        content = "";
        grade = "";
        id = "";
        name = "";
        description = "";
        uid = "";
    }

    public SubmissionManager(String content, String grade,String id, String name, String description, String uid) {
        this.content = content;
        this.grade = grade;
        this.id = id;
        this.name = name;
        this.description = description;
        this.uid = uid;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getGrade() {
        return grade;
    }

    public void setGrade(String grade) {
        this.grade = grade;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getUid() {
        return uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
