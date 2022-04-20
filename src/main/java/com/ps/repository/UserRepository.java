package com.ps.repository;

import com.ps.domain.Project;
import com.ps.domain.User;
import com.ps.domain.UserProject;
import com.ps.dto.ProjectDto;
import org.hibernate.HibernateException;

import java.util.List;

public interface UserRepository {
    void createUser(User user);

    User findById(int userId);

    void assignProjectForUser(UserProject userProject);

    List<Project> getProjectsByUserId(int userId);

    List<User> getListOfUserByProjectId(int projectId);

    void updateUserById(int userId,String name);

}
