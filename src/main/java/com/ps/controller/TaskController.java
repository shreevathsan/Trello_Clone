package com.ps.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ps.domain.Project;
import com.ps.domain.Task;
import com.ps.domain.User;
import com.ps.dto.TaskDto;
import com.ps.service.ProjectService;
import com.ps.service.TaskService;
import com.ps.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import javax.json.Json;
import javax.json.JsonObject;
import javax.json.JsonReader;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.InputStream;
import java.io.PrintWriter;

@WebServlet(urlPatterns = {"/tasks"})
public class TaskController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(TaskController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            TaskDto taskDto = new Gson().fromJson(req.getReader(), TaskDto.class);
            int projectId = taskDto.getProjectId();
            ProjectService projectService = new ProjectService();
            Project project = projectService.getProject(projectId);
            int userId = taskDto.getUserId();
            User user = new UserService().getUser(userId);
            logger.info("about to call the service class");
            TaskService taskService = new TaskService();
            String s = taskService.creatTask(taskDto, project, user, projectId, userId);
            PrintWriter writer = resp.getWriter();
            if (s.equals("saved")) {
                writer.write("task saved successfully");
                writer.flush();
                resp.setStatus(200);
            }

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
            PrintWriter writer=resp.getWriter();
            writer.write("this user does not have the access to take up the task");
            writer.flush();
            resp.setStatus(500);
        }
    }

    /***
     * To get the task name and list of task associated to it by passing taskId
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int taskId = Integer.parseInt(req.getParameter("taskId"));
            logger.info("got the request from the client");
            TaskService taskService = new TaskService();
            logger.info("about to call the service class");
            Task task = taskService.getTaskAndActivitiesById(taskId);
            PrintWriter printWriter = resp.getWriter();
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String result = gson.toJson(task);
            printWriter.write(result);
            resp.setStatus(200);
            resp.setContentType("application/json");
            printWriter.flush();


        } catch (NumberFormatException e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }

    /**
     * To update the status of the task by passing taskId
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream();
             JsonReader jsonReader = Json.createReader(inputStream)) {
            JsonObject jsonObject = jsonReader.readObject();
            int taskId = jsonObject.getInt("taskId");
            String status = jsonObject.getString("status");
            logger.info("got the request from the client" + taskId + " " + status);
            TaskService taskService = new TaskService();
            logger.info("about to call the service class");
            taskService.updateStatusOfTheTask(taskId, status);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("status updated successfully");
            resp.setStatus(200);
            printWriter.flush();


        } catch (NumberFormatException e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }

    /**
     * to delete the task by passing taskId
     */


    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("taskId"));
            logger.info("got the request from the client" + id);
            TaskService taskService = new TaskService();
            logger.info("about to call the service class by passing" + id);
            String result = taskService.deleteTask(id);
            PrintWriter printWriter = resp.getWriter();
            if (result.equals("deleted")) {
                printWriter.write("task deleted successfully");
                resp.setStatus(200);
            } else {
                printWriter.write("deletion unsuccessfull or the data is not present");
                printWriter.flush();
                resp.setStatus(500);
            }
            printWriter.flush();
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            PrintWriter writer = resp.getWriter();
            writer.write("task is not present or already deleted");
            resp.setStatus(500);
        }
    }
}

