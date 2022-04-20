package com.ps.dto;

public class ActivityDto {
    private String comment;
    private String date;
    private int userId;
    private int taskId;

    public ActivityDto(String comment, String date, int userId, int taskId) {
        this.comment = comment;
        this.date = date;
        this.userId = userId;
        this.taskId = taskId;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getTaskId() {
        return taskId;
    }

    public void setTaskId(int taskId) {
        this.taskId = taskId;
    }
}
