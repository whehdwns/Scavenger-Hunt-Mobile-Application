package com.example.login.support;

public class TaskManager {
    private String type;
    private String description;
    private int timeStart;
    private int timeEnd;

    public TaskManager() {
        type = "";
        description = "";
        timeStart = 0;
        timeEnd = 0;
    }

    public TaskManager(String type, String description, int timeStart, int timeEnd) {
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

    public int getTimeStart() {
        return timeStart;
    }

    public void setTimeStart(int timeStart) {
        this.timeStart = timeStart;
    }

    public int getTimeEnd() {
        return timeEnd;
    }

    public void setTimeEnd(int timeEnd) {
        this.timeEnd = timeEnd;
    }
}
