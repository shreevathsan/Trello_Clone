package com.ps.trelloapp.repository;

import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.domain.UserProject;
import com.ps.trelloapp.dto.ProjectDto;
import com.ps.trelloapp.dto.UserDto;


import java.util.List;

public interface UserRepository {
    List<ProjectDto> getProjectsById(int userId);

    String saveUser(User user);

    User getUseryId(int userId);

    boolean assignUserForProject(UserProject userProject);

    List<UserDto> getUsersForTheProject(int projectId);

}

