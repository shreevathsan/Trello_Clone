package com.ps.trelloapp.controller;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.Activity;
import com.ps.trelloapp.domain.Project;
import com.ps.trelloapp.domain.Task;
import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.dto.TaskDto;
import com.ps.trelloapp.service.TaskService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.List;


@RestController
@RequestMapping("/tasks")
public class TaskController {
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    private final TaskService taskService;

    @Autowired
    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @PostMapping
    public void createTask(@RequestBody TaskDto taskDto, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to create task for the project :" + taskDto.getProjectId());
        if (taskDto == null) {
            throw new NotFoundException("");
        }
        boolean result = taskService.createTask(taskDto, taskDto.getProjectId(), taskDto.getUserId());
        PrintWriter writer = response.getWriter();
        if (result == true) {

            writer.write("Task Created Successfully");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("This task cant be created for this user");
            writer.flush();
            response.setStatus(500);
        }


    }

    @GetMapping("/{taskId}")
    public Task getTask(@PathVariable int taskId) {
        logger.info("got the request from the client to get the task for id :" + taskId);
        Task task = null;

        try {
            task = taskService.getTaskAndActivityByTaskId(taskId);
            Project project = task.getProject();
            project.setUser(null);
            project.setTasks(null);
            project.setUserList(null);
            User user = task.getUser();
            user.setProjectList(null);
            user.setTasks(null);
            user.setActivities(null);
            List<Activity> activityList=task.getActivities();
            for (Activity activity : activityList) {
                User user1 = activity.getUser();
                user1.setTasks(null);
                user1.setProjectList(null);
                user1.setActivities(null);
                Task task1 = activity.getTask();
                task1.setActivities(null);
                task1.setUser(null);
            }
            logger.info("KLLLLLLLLLLLLLL" + task);

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return task;
    }

    @PutMapping
    public void updateTask(@RequestBody TaskDto taskDto, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to update the task :" + taskDto.getTitle());
        logger.info("$$$$$$$$$$$$$"+taskDto);
        if (taskDto == null) {
            throw new NotFoundException("");
        }
        boolean result = taskService.updateTask(taskDto, taskDto.getProjectId(), taskDto.getUserId());
        PrintWriter writer = response.getWriter();
        if (result == true) {
            writer.write("task updated successfully");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("task not updated");
            writer.flush();
            response.setStatus(500);
        }
    }

    @DeleteMapping("/{taskId}")
    public void deleteTask(@PathVariable int taskId, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to delete task for id :" + taskId);
        boolean result = taskService.deleteTask(taskId);
        PrintWriter writer = response.getWriter();
        if (result == true) {
            writer.write("Task Deleted Successfully");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("Task not deleted");
            writer.flush();
            response.setStatus(500);
        }


    }


}
