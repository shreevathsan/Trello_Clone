package com.ps.controller;

import com.google.gson.Gson;
import com.ps.domain.Task;
import com.ps.domain.User;
import com.ps.dto.ActivityDto;
import com.ps.service.ActivityService;
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


@WebServlet(urlPatterns = {"/activities"})
public class ActivityController extends HttpServlet {
    private static final Logger logger = LogManager.getLogger(ActivityController.class);

    @Override
    protected void doPost(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try {
            logger.info("got request from the client");
            ActivityDto activityDto = new Gson().fromJson(req.getReader(), ActivityDto.class);

            UserService userService = new UserService();
            User user = userService.getUser(activityDto.getUserId());

            TaskService taskService = new TaskService();
            Task task = taskService.getTaskById(activityDto.getTaskId());

            logger.info("about to call the activity service class");
            ActivityService activityService = new ActivityService();
            activityService.createActivity(activityDto, user, task);
            resp.setStatus(200);


        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            resp.setStatus(500);
        }
    }

    @Override
    protected void doPut(HttpServletRequest req, HttpServletResponse resp) throws ServletException, IOException {
        try(InputStream inputStream=req.getInputStream();
            JsonReader jsonReader= Json.createReader(inputStream)) {
            JsonObject jsonObject=jsonReader.readObject();
            int id=jsonObject.getInt("activityId");
            String comment=jsonObject.getString("comment");
            logger.info("got the request from the client" + id+" "+comment);
            ActivityService activityService = new ActivityService();
            logger.info("about to call the service class");
            activityService.updateActivity(id,comment);
        }


    }
}

