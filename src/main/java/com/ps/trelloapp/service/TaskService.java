package com.ps.trelloapp.service;

import com.ps.trelloapp.domain.Project;
import com.ps.trelloapp.domain.Task;
import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.dto.TaskDto;
import com.ps.trelloapp.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TaskService {

    private static final Logger logger = LogManager.getLogger(TaskService.class);

    private final TaskRepository taskRepository;

    private final ProjectService projectService;

    private final UserService userService;

    @Autowired
    public TaskService(TaskRepository taskRepository, ProjectService projectService, UserService userService) {
        this.taskRepository = taskRepository;
        this.projectService = projectService;
        this.userService = userService;
    }

    @Transactional
    public boolean createTask(TaskDto taskDto, int projectId, int userId) {
        logger.info("got the request from the client to create Task for title :" + taskDto.getTitle());
        Project project = projectService.getProjectById(projectId);
        User user = userService.getUserById(userId);
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.getStatus(), taskDto.getStartDate(), taskDto.getEndDate(), project, user);
        return taskRepository.createTask(task, projectId, userId);

    }

    @Transactional
    public Task getTaskAndActivityByTaskId(int taskId) {
        logger.info("got the request from the controller class to get the task :" + taskId);
        return taskRepository.getTaskAndActivitiesByTaskId(taskId);
    }

    @Transactional
    public boolean updateTask(TaskDto taskDto, int projectId, int userId) {
        logger.info("got the request from the controller class :" + taskDto.getTitle());
        Project project = projectService.getProjectById(projectId);
        User user = userService.getUserById(userId);
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.getStatus(), taskDto.getStartDate(), taskDto.getEndDate(), project, user);
        return taskRepository.updateTask(task, taskDto.getId());

    }

    @Transactional
    public boolean deleteTask(int taskId)
    {
        logger.info("got the data from the controller class to delete task :"+taskId);
        return taskRepository.deleteTask(taskId);
    }

    @Transactional
    public Task getTaskByTaskId(int taskId)
    {
        return taskRepository.getTaskById(taskId);
    }
}
