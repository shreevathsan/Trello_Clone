package com.ps.trelloapp.controller;


import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.ApiResponse;
import com.ps.trelloapp.dto.ProjectDto;
import com.ps.trelloapp.dto.UserDto;
import com.ps.trelloapp.service.UserService;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);

    private final UserService userService;


    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/{userId}")
    public ApiResponse getProjectsById(@PathVariable int userId) {
        logger.info("got the request from the client to get the projects for id" + userId);
        List<ProjectDto> projectDtos = userService.getProjectsById(userId);
        ApiResponse apiResponse = new ApiResponse();
        apiResponse.setOk(true);
        apiResponse.setMessage("got the projects for the user successfully");
        apiResponse.setProjectDtos(projectDtos);
        apiResponse.setDate(new Date());
        return apiResponse;

    }

    @PostMapping
    public ApiResponse saveUser(@RequestBody UserDto userDto) {
        logger.info("got the request from the user to create a profile");
        ApiResponse apiResponse = new ApiResponse();
        if (userDto.getName().isEmpty() == false && userDto.getName().isBlank() == false) {
            String result = userService.saveUser(userDto);

            if (result.equals("success")) {
                apiResponse.setOk(true);
                apiResponse.setDate(new Date());
                apiResponse.setMessage("user created successfully");
            }
        } else {
            throw new NotFoundException("");
        }
        return apiResponse;
    }
}
