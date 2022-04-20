package com.ps.dto;

public class ProjectDto {
    private String name;
    private int ownerId;
    private int userId;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getOwnerId() {
        return ownerId;
    }

    public void setOwnerId(int ownerId) {
        this.ownerId = ownerId;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public ProjectDto(String name, int ownerId, int user_id) {
        this.name = name;
        this.ownerId = ownerId;
        this.userId = user_id;
    }
}
