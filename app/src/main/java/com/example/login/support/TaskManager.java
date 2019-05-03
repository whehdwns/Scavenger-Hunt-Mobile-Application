package com.example.login.support;

public class TaskManager {
    private String type;
    private String description;
    private String timeStart;
    private String timeEnd;

    public TaskManager() {
        type = "";
        description = "";
        timeStart = "";
        timeEnd = "";
    }

    public TaskManager(String type, String description, String timeStart, String timeEnd) {
        this.type = type;
        this.description = description;
        this.timeStart = timeStart;
        this.timeEnd = timeEnd;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(String timeStart) {
        this.timeStart = timeStart;
    }

    public String getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(String timeEnd) {
        this.timeEnd = timeEnd;
    }
}
