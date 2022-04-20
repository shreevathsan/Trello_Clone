package com.ps.controller;

import com.google.gson.Gson;
import com.ps.domain.Project;
import com.ps.domain.User;
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

@WebServlet(urlPatterns = {"/users"})
public class UserController extends HttpServlet {
    private static Logger logger = LogManager.getLogger(UserController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream = req.getInputStream();
             JsonReader jsonReader = Json.createReader(inputStream)) {
            JsonObject jsonObject = jsonReader.readObject();
            User user = new User(jsonObject.getString("name"));
            logger.info("got request from the client" + user.getName());
            UserService userService = new UserService();
            logger.info("about to call the service class");
            userService.createUser(user);


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            PrintWriter printWriter = resp.getWriter();
            printWriter.write("user not created");
            printWriter.flush();
        }
    }

    /**
     * To get the List of project for a user by passing userId
     *
     * @param req
     * @param resp
     * @throws ServletException
     * @throws IOException
     */

    @Override
    protected void doGet(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            int userId = Integer.parseInt(req.getParameter("userId"));
            logger.info("got the request from the user" + userId);
            UserService userService = new UserService();
            logger.info("about to call the service class");
            List<Project> projectList = userService.getProjectsById(userId);
            String result = new Gson().toJson(projectList);
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

    /***
     * to update the name of the user by passing userId and the value
     */
    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try (InputStream inputStream=req.getInputStream();
            JsonReader jsonReader=Json.createReader(inputStream)){
            JsonObject jsonObject=jsonReader.readObject();
            int userId=jsonObject.getInt("userId");
            String name=jsonObject.getString("name");
            logger.info("got the request from the user"+userId+" "+name);
            UserService userService = new UserService();
            logger.info("about to call the service class");
            userService.updateUserById(userId,name);
            PrintWriter writer = resp.getWriter();
            writer.write("name Updated successfully");
            resp.setStatus(200);
            writer.flush();


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }
}
