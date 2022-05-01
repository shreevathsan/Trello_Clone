package com.ps.trelloapp.service;

import com.ps.trelloapp.domain.Project;
import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.dto.ProjectDto;
import com.ps.trelloapp.repository.ProjectRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProjectService {
    private static final Logger logger = LogManager.getLogger(ProjectService.class);

    private final ProjectRepository projectRepository;



    @Autowired
    public ProjectService(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Transactional
    public boolean createProject(ProjectDto projectDto, User user) {
        logger.info("got the data from the controller class to create project with name :" + projectDto.getName());
        Project project=new Project();
        List<User> userList=new ArrayList<>();
        userList.add(user);
        project.setName(projectDto.getName());
        project.setUser(user);
        project.setUserList(userList);
        return projectRepository.createProject(project);

    }

    @Transactional
    public Project getProjectAndTaskByprojectId(int projectId)
    {
        logger.info("got the data from the controller class to get Project by id :"+projectId);
        return projectRepository.getProjectAndTaskByProjectId(projectId);
    }

    @Transactional
    public void updateProject(ProjectDto projectDto,User user)
    {
        logger.info("got the data from the controller class to update project :"+projectDto.getName());
        Project project=new Project(projectDto.getName(),projectDto.getId(),user);
        projectRepository.updateProject(project);
    }

    @Transactional
    public void deleteProject(int id)
    {
        logger.info("got the request from the client to delete project for id :"+id);
        projectRepository.deleteProject(id);
    }

    @Transactional
    public Project getProjectById(int projectId)
    {
        return projectRepository.getProjectById(projectId);
    }
}
