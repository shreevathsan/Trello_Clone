package com.ps.trelloapp.repository;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.domain.UserProject;
import com.ps.trelloapp.dto.ProjectDto;
import com.ps.trelloapp.dto.UserDto;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;

import javax.persistence.TypedQuery;
import java.util.List;

@Repository
public class UserRepositoryImpl implements UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public UserRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public List<ProjectDto> getProjectsById(int userId) {
        logger.info("about to get the projects for the user haing id:" + userId);
        List<ProjectDto> projectDtos = null;
        try {
            TypedQuery<ProjectDto> query = entityManager
                    .createQuery("select new com.ps.trelloapp.dto.ProjectDto(p.id,p.name,u.userId) " +
                            "from Project p " +
                            "join UserProject u on p.id=u.projectId " +
                            "where u.userId=" + userId, ProjectDto.class);
            projectDtos = query.getResultList();
            logger.info("got the projects for the user:" + projectDtos);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }

        if (projectDtos != null) {
            return projectDtos;
        } else {
            throw new NotFoundException("The user id you have entered is incorrect");
        }

    }


    public String saveUser(User user) {
        try {


            logger.info("about to create the profile for the user for name: " + user.getName());

            entityManager.persist(user);
            return "success";
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);

        }
        return null;
    }

    public User getUseryId(int userId) {
        User user = null;
        try {
            user = entityManager.getReference(User.class, userId);

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return user;
    }

    public boolean assignUserForProject(UserProject userProject) {
        logger.info("about to assign project for user");
        try {
            entityManager.persist(userProject);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return true;
    }


    public List<UserDto> getUsersForTheProject(int projectId) {
        logger.info("about to get the users for the project :" + projectId);
        List<UserDto> userDtos = null;
        try {
            TypedQuery<UserDto> query= (TypedQuery<UserDto>) entityManager.createQuery("select new com.ps.trelloapp.dto.UserDto(u.name) from User u join UserProject p on u.id=p.userId where p.projectId='"+projectId+"'");
             userDtos=query.getResultList();
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        logger.info("%%%%%%%%%%%" + userDtos);
        if (userDtos == null) {
            throw new NotFoundException("");

        }
        return userDtos;
    }
}
