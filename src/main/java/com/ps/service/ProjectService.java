package com.ps.service;

import com.ps.domain.Project;
import com.ps.domain.User;
import com.ps.dto.ProjectDto;
import com.ps.repository.ProjectRepoImpl;
import com.ps.repository.ProjectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.util.List;

public class ProjectService {
    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    public void createProject(ProjectDto projectDto, User user, List<User> userList) {
        logger.info("got data from the controller class");
        Project project = new Project(projectDto.getName(), user, userList);
        logger.info("about to call the implementation class");
        ProjectRepository projectRepository = new ProjectRepoImpl();
        projectRepository.createProject(project);

    }

    public Project getProject(int projectId) {
        logger.info("got the request from the controller to get project by " + projectId);
        ProjectRepository projectRepository = new ProjectRepoImpl();
        return projectRepository.getProjectById(projectId);
    }

    public Project getProjectAndTaskByProjectId(int projectId) {
        logger.info("got the request from the controller class");
        ProjectRepository projectRepository = new ProjectRepoImpl();
        logger.info("about to call the service class");
        Project project = projectRepository.getProjectAndTaskByProjectId(projectId);
        logger.info("#########***????project" + project);
        return project;
    }
    public String deleteProject(int id)
    {
        logger.info("got the data from the controller class");
        ProjectRepository projectRepository=new ProjectRepoImpl();
        logger.info("about to cal the impl class"+id);
        return projectRepository.deleteProject(id);
    }
    public void updateProjectName(int id,String name)
    {
      logger.info("got the data from the controller class"+id+" "+name);
      ProjectRepository projectRepository=new ProjectRepoImpl();
      logger.info("about to call the impl class");
      projectRepository.updateProjectName(id,name);
    }
}
