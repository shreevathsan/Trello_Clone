package com.ps.trelloapp.controller;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.Project;
import com.ps.trelloapp.domain.Task;
import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.dto.ProjectDto;
import com.ps.trelloapp.service.ProjectService;
import com.ps.trelloapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;

@RestController
@RequestMapping("/projects")
public class ProjectController {
    private static final Logger logger = LogManager.getLogger(ProjectController.class);

    private final ProjectService projectService;

    private final UserService userService;

    @Autowired
    public ProjectController(ProjectService projectService, UserService userService) {
        this.projectService = projectService;
        this.userService = userService;
    }

    @PostMapping
    public void createProject(@RequestBody ProjectDto projectDto, HttpServletResponse response) {
        logger.info("got the request from the client to create project for name :" + projectDto.getName());
        try {
            User user = userService.getUserById(projectDto.getUserId());
            boolean result = projectService.createProject(projectDto, user);
            if (result == true) {
                PrintWriter printWriter = response.getWriter();
                printWriter.write("Project Created Successfully");
                printWriter.flush();

            }

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
    }

    @GetMapping("/{projectId}")
    public Project getTaskByProjectId(@PathVariable int projectId) {
        logger.info("got the request from the client to get the Project and task associated with it by passing projectId :" + projectId);

        Project project = projectService.getProjectAndTaskByprojectId(projectId);
        project.setUserList(null);
        User user = project.getUser();
        user.setProjectList(null);
        user.setActivities(null);
        user.setTasks(null);
        List<Task> tasks = project.getTasks();
        for (Task task : tasks) {
            task.setActivities(null);
            task.setProject(null);
            task.setUser(null);
        }
        return project;
    }


    @PutMapping
    public void updateProject(@RequestBody ProjectDto projectDto, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to update project name for the project :" + projectDto.getName());
        if (projectDto != null) {
            User user = userService.getUserById(projectDto.getUserId());
            projectService.updateProject(projectDto, user);
            PrintWriter printWriter = response.getWriter();
            printWriter.write("Updated Successfuly");
        } else {
            throw new NotFoundException("");
        }
    }

    @DeleteMapping
    public void deleteProjectById(@RequestBody ProjectDto projectDto,HttpServletResponse response) throws IOException {
        logger.info("got the request from the client for deleting the the project for id :" + projectDto.getId());
        if (projectDto != null) {
            projectService.deleteProject(projectDto.getId());
            PrintWriter printWriter=response.getWriter();
            printWriter.write("Deleted Successfully");
            response.setStatus(200);
        } else {
            throw new NotFoundException("");
        }


    }

}

