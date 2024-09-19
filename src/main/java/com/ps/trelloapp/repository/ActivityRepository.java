package com.ps.trelloapp.repository;

import com.ps.trelloapp.domain.Activity;

public interface ActivityRepository {

    public boolean createActivity(Activity activity);

    public boolean updateActivity(Activity activity,int id);

    public boolean deleteActivity(int activityId);
}
