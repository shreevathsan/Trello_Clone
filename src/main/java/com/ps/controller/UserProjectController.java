package com.ps.controller;

import com.google.gson.Gson;
import com.ps.domain.User;
import com.ps.domain.UserProject;
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
import java.util.List;


@WebServlet(urlPatterns = {"/userproject"})
public class UserProjectController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(UserProjectController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream();
             JsonReader jsonReader = Json.createReader(inputStream)) {
            JsonObject jsonObject = jsonReader.readObject();
            UserProject userProject = new UserProject(jsonObject.getInt("userId"), jsonObject.getInt("projectId"));
            logger.info("got request from the client");
            UserService userService = new UserService();
            logger.info("about to call the service class");
            userService.assignProjectForUser(userProject);
            resp.setStatus(200);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }

    }

    /***
     * To get the list of users involved in a particular project by passing projectId
     *
     */
    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int projectId = Integer.parseInt(req.getParameter("projectId"));
            logger.info("got the request from the client" + projectId);
            UserService userService = new UserService();
            logger.info("about to call th service class");
            List<User> userList = userService.getListOfUserByProjectId(projectId);
            String result = new Gson().toJson(userList);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write(result);
            resp.setStatus(200);
            resp.setContentType("application/json");
            printWriter.flush();


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }
}
