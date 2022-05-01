package com.ps.trelloapp.service;

import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.domain.UserProject;
import com.ps.trelloapp.dto.ProjectDto;
import com.ps.trelloapp.dto.UserDto;
import com.ps.trelloapp.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    @Transactional
    public List<ProjectDto> getProjectsById(int userId) {
        logger.info("got the data from the controller class to get the list of projects for id:" + userId);
        List<ProjectDto> projectDtos = userRepository.getProjectsById(userId);
        return projectDtos;
    }

    @Transactional
    public String saveUser(UserDto userDto) {
        logger.info("got the data from the user to create profile for name: " + userDto.getName());
        User user = new User(userDto.getName());
        return userRepository.saveUser(user);
    }

    @Transactional
    public User getUserById(int userId) {
        logger.info("about to call the userimpl class to get the user By passing userId :" + userId);
        return userRepository.getUseryId(userId);
    }

    @Transactional
    public boolean assignProjectForUser(UserProject userProject) {
        logger.info("got the data from the controller to assign project for user");
        return userRepository.assignUserForProject(userProject);
    }

    @Transactional
    public List<UserDto> getUsersForTheProject(int projectId) {
        logger.info("got the data from the controller to get users for the project" + projectId);
        return userRepository.getUsersForTheProject(projectId);

    }
}
