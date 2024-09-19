package com.ps.trelloapp.controller;

import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.domain.UserProject;
import com.ps.trelloapp.dto.UserDto;
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
@RequestMapping("/userprojects")
public class UserProjectController {

    private static final Logger logger = LogManager.getLogger(UserProjectController.class);

    private final UserService userService;

    @Autowired
    public UserProjectController(UserService userService) {
        this.userService = userService;
    }

    @PostMapping
    public void assignUserForProject(@RequestBody UserProject userProject, HttpServletResponse response) throws IOException {
        logger.info("got the request from the client to assign project for the user");
        boolean result = userService.assignProjectForUser(userProject);
        PrintWriter writer = response.getWriter();
        if (result == true) {
            writer.write("project assigned successfully for the user");
            writer.flush();
            response.setStatus(200);
        } else {
            writer.write("project not assigned");
            writer.flush();
            response.setStatus(500);
        }
    }

    @GetMapping("/{projectId}")
    public List<UserDto> getUsersForTheProject(@PathVariable int projectId, HttpServletResponse response) {
        logger.info("got the request from the client to get the users for the specific project");
        List<UserDto> userList = userService.getUsersForTheProject(projectId);
        return userList;

    }
}
