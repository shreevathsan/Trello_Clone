package com.ps.controller;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.ps.domain.Project;
import com.ps.domain.User;
import com.ps.dto.ProjectDto;
import com.ps.service.ProjectService;
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
import java.util.ArrayList;
import java.util.List;

@WebServlet(urlPatterns = {"/projects"})
public class ProjectController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ProjectController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            ProjectDto projectDto = new Gson().fromJson(req.getReader(), ProjectDto.class);
            logger.info("got the request from the user");
            int ownerId = projectDto.getUserId();
            User user = new UserService().getUser(ownerId);
            List<User> userList = new ArrayList<>();
            userList.add(user);
            ProjectService projectService = new ProjectService();
            logger.info("about to call the service class");
            projectService.createProject(projectDto, user, userList);
            resp.setStatus(200);


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            logger.error("Error creating project");
            resp.setStatus(500);
        }

    }

    /**
     * To get the project name and list of task associated with by passing
     * projectId
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int projectId = Integer.parseInt(req.getParameter("projectId"));
            logger.info("got the request from the client" + projectId);
            ProjectService projectService = new ProjectService();
            logger.info("about to call the service class");
            Project project = projectService.getProjectAndTaskByProjectId(projectId);
            Gson gson = new GsonBuilder().excludeFieldsWithoutExposeAnnotation().create();
            String result = gson.toJson(project);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(result);
            resp.setContentType("application/json");
            resp.setStatus(200);
            printWriter.flush();


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }

    @Override
    protected void doDelete(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int id = Integer.parseInt(req.getParameter("projectId"));
            logger.info("got the request from the client" + id);
            ProjectService projectService = new ProjectService();
            logger.info("about to call the service class");
            String result = projectService.deleteProject(id);
            PrintWriter writer = resp.getWriter();
            if (result.equals("deleted")) {
                writer.write("project deleted successfully");
                writer.flush();
                resp.setStatus(200);
            }


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
            PrintWriter writer = resp.getWriter();
            writer.write("project not found or project is already deleted");
            writer.flush();
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream();
             JsonReader jsonReader = Json.createReader(inputStream)) {
            JsonObject jsonObject = jsonReader.readObject();
            int id = jsonObject.getInt("projectId");
            String name = jsonObject.getString("name");
            logger.info("got the request from the client" + id + " " + name);
            ProjectService projectService = new ProjectService();
            logger.info("about to call the service class");
            projectService.updateProjectName(id, name);
            PrintWriter writer = resp.getWriter();
            writer.write("name updated successfully");
            writer.flush();
            resp.setStatus(200);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }
}
