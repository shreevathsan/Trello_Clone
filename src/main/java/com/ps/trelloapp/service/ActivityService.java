package com.ps.trelloapp.service;

import com.ps.trelloapp.domain.Activity;
import com.ps.trelloapp.domain.Task;
import com.ps.trelloapp.domain.User;
import com.ps.trelloapp.dto.ActivityDto;
import com.ps.trelloapp.repository.ActivityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class ActivityService {
    private static final Logger logger = LogManager.getLogger(ActivityService.class);

    private final ActivityRepository activityRepository;

    private final UserService userService;

    private final TaskService taskService;

    @Autowired
    public ActivityService(ActivityRepository activityRepository, UserService userService, TaskService taskService) {
        this.activityRepository = activityRepository;
        this.userService = userService;
        this.taskService = taskService;
    }

    @Transactional
    public boolean createActivity(ActivityDto activityDto) {
        logger.info("got the data from the controller class to create activity " + activityDto.getDate());
        User user = userService.getUserById(activityDto.getUserId());
        Task task = taskService.getTaskByTaskId(activityDto.getTaskId());
        Activity activity = new Activity(activityDto.getComment(), activityDto.getDate(), user, task);
        return activityRepository.createActivity(activity);
    }

    @Transactional
    public boolean updateActivity(ActivityDto activityDto) {
        logger.info("got the data from the controller class to update activity " + activityDto.getDate());
        User user = userService.getUserById(activityDto.getUserId());
        Task task = taskService.getTaskByTaskId(activityDto.getTaskId());
        Activity activity = new Activity(activityDto.getComment(), activityDto.getDate(), user, task);
        return activityRepository.updateActivity(activity, activityDto.getId());
    }

    @Transactional
    public boolean deleteActivity(int id) {
        logger.info("got the request from the client to delete activity for id :" + id);
        return activityRepository.deleteActivity(id);
    }
}
