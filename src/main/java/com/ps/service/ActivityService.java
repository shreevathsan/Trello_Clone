package com.ps.service;

import com.ps.domain.Activity;
import com.ps.domain.Task;
import com.ps.domain.User;
import com.ps.dto.ActivityDto;
import com.ps.repository.ActivityRepoImpl;
import com.ps.repository.ActivityRepository;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class ActivityService {
    private static final Logger logger = LogManager.getLogger(ActivityService.class);

    public void createActivity(ActivityDto activityDto, User user, Task task) {
        logger.info("got request from the controller class");
        Activity activity = new Activity(activityDto.getComment(), activityDto.getDate(), user, task);
        logger.info("about to call the implementation class");
        ActivityRepository activityRepository = new ActivityRepoImpl();
        activityRepository.createActivity(activity);


    }
    public void updateActivity(int id,String comment)
    {
        logger.info("got the data from the controller class");
        ActivityRepository activityRepository=new ActivityRepoImpl();
        logger.info("about to call the impl class"+id);
        activityRepository.updateActivity(id,comment);
    }

}
