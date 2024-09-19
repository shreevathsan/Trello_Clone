package com.ps.trelloapp.domain;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_project")
public class UserProject {
    @Id
    @Column(name = "user_id")
    private int userId;

    @Column(name = "project_id")
    private int projectId;

    public UserProject() {
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public int getProjectId() {
        return projectId;
    }

    public void setProjectId(int projectId) {
        this.projectId = projectId;
    }

    public UserProject(int userId, int projectId) {
        this.userId = userId;
        this.projectId = projectId;
    }
}
