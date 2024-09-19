package com.ps.trelloapp.controller;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.dto.ActivityDto;
import com.ps.trelloapp.service.ActivityService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;


@RestController
@RequestMapping("/activities")
public class ActivityController {
    private static final Logger logger = LogManager.getLogger(ActivityController.class);

    private final ActivityService service;

    @Autowired
    public ActivityController(ActivityService service) {
        this.service = service;
    }

    @PostMapping
    public void createActivity(@RequestBody ActivityDto activityDto, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to create the activity" + activityDto.getComment());
        boolean result = service.createActivity(activityDto);
        PrintWriter writer = response.getWriter();
        if (result == true) {
            writer.write("activity created successfully");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("Activity not created");
            writer.flush();
            response.setStatus(500);
        }


    }

    @PutMapping
    public void updateActivity(@RequestBody ActivityDto activityDto, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to update activity" + activityDto.getComment());
        if (activityDto == null) {
            throw new NotFoundException("");
        }
        boolean result = service.updateActivity(activityDto);
        PrintWriter writer = response.getWriter();
        if (result == true) {
            writer.write("activity updated successfully");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("Activity not updated");
            writer.flush();
            response.setStatus(500);
        }
    }

    @DeleteMapping("/{id}")
    public void deleteActivity(@PathVariable int id, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to delete activity for id :" + id);
        boolean result = service.deleteActivity(id);
        PrintWriter writer = response.getWriter();
        if (result == true) {
            writer.write("activity deleted successfully");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("Activity not deleted");
            writer.flush();
            response.setStatus(500);
        }

    }
}
