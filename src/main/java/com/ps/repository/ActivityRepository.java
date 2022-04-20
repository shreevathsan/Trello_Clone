package com.ps.repository;

import com.ps.domain.Activity;

public interface ActivityRepository {
    void createActivity(Activity activity);
    void updateActivity(int id,String comment);
}
