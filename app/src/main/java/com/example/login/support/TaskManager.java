package com.example.login.support;

public class TaskManager {
    private String questionType;
    private String taskDescription;

    public TaskManager() {
        questionType = "";
        taskDescription = "";
    }

    public TaskManager(String questionType, String taskDescription) {
        this.questionType = questionType;
        this.taskDescription = taskDescription;
    }

    public String getQuestionType() {
        return questionType;
    }

    public void setQuestionType(String questionType) {
        this.questionType = questionType;
    }

    public String getTaskDescription() {
        return taskDescription;
    }

    public void setTaskDescription(String taskDescription) {
        this.taskDescription = taskDescription;
    }

}
