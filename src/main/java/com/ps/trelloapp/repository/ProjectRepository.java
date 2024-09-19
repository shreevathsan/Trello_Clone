package com.ps.trelloapp.repository;

import com.ps.trelloapp.domain.Project;

public interface ProjectRepository {
    public boolean createProject(Project project);

    public Project getProjectAndTaskByProjectId(int projectId);

    public void updateProject(Project project);

    public void deleteProject(int id);

    public Project getProjectById(int projectId);
}
