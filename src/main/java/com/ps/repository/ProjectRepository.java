package com.ps.repository;

import com.ps.domain.Project;

public interface ProjectRepository {
    void createProject(Project project);
    Project getProjectById(int projectId);
    Project getProjectAndTaskByProjectId(int projectId);
    String deleteProject(int id);
    void updateProjectName(int id,String name);
}
