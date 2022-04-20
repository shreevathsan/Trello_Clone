package com.ps.service;

import com.ps.domain.Project;
import com.ps.domain.Task;
import com.ps.domain.User;
import com.ps.dto.TaskDto;
import com.ps.repository.TaskRepoImpl;
import com.ps.repository.TaskRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class TaskService {
    private static final Logger logger = LogManager.getLogger(TaskService.class);

    public String creatTask(TaskDto taskDto, Project project, User user,int projectId,int userId) {
        Task task = new Task(taskDto.getTitle(), taskDto.getDescription(), taskDto.getStatus(), taskDto.getStartDate(), taskDto.getEndDate(), project, user);
        logger.info("about to call the implementation class");
        TaskRepository taskRepository = new TaskRepoImpl();
        return taskRepository.createTask(task,projectId,userId);
    }

    public Task getTaskById(int taskId) {
        logger.info("got the request from the controller" + taskId);
        TaskRepository taskRepository = new TaskRepoImpl();
        logger.info("about to call the implementation class to get task");
        return taskRepository.getTaskById(taskId);
    }

    public Task getTaskAndActivitiesById(int taskId) {
        logger.info("got data from the controller class");
        TaskRepository taskRepository = new TaskRepoImpl();
        logger.info("about to call the implementation class");
        return taskRepository.getTaskAndActivitiesById(taskId);
    }

    public void updateStatusOfTheTask(int taskId, String status) {
        logger.info("got the request from the controller class");
        TaskRepository taskRepository = new TaskRepoImpl();
        logger.info("about to call the implementation class");
        taskRepository.updateStatusOfTheTask(taskId, status);
    }

    public String deleteTask(int id) {
        logger.info("got the data from the controller class");
        TaskRepository taskRepository = new TaskRepoImpl();
        logger.info("about to call the impl class");
        return taskRepository.deleteTask(id);
    }
}

