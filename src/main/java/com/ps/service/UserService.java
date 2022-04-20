package com.ps.service;

import com.ps.domain.Project;
import com.ps.domain.User;
import com.ps.domain.UserProject;
import com.ps.repository.UserRepoImpl;
import com.ps.repository.UserRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import java.util.List;


public class UserService {
    private static final Logger logger = LogManager.getLogger(UserService.class);

    public void createUser(User user) {
        logger.info("got the data from the controller class");
        UserRepository userRepository = new UserRepoImpl();
        logger.info("about to call the implementation class");
        userRepository.createUser(user);
    }

    public User getUser(int userId) {
        logger.info("about to get the user details for: " + userId);
        User user = new UserRepoImpl().findById(userId);
        logger.info("got user from DB: " + user);
        return user;
    }

    public void assignProjectForUser(UserProject userProject) {
        logger.info("got the data from the controller class");
        UserRepository userRepository = new UserRepoImpl();
        logger.info("about to call the implementation class");
        userRepository.assignProjectForUser(userProject);
    }

    public List<Project> getProjectsById(int userId) {
        logger.info("got the data from the service class");
        UserRepository userRepository = new UserRepoImpl();
        logger.info("about to call the implementation class");
        return userRepository.getProjectsByUserId(userId);
    }

    public List<User> getListOfUserByProjectId(int projectId)
    {
        logger.info("got the data from the controller class");
        UserRepository userRepository=new UserRepoImpl();
        logger.info("about to call the implementation class");
        return userRepository.getListOfUserByProjectId(projectId);
    }
    public void updateUserById(int userId,String name)
    {
        logger.info("got the data from the controller class");
        UserRepository userRepository=new UserRepoImpl();
        logger.info("about to call the implementation class");
        userRepository.updateUserById(userId,name);

    }
}
