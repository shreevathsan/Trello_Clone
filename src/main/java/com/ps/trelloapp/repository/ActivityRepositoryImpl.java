package com.ps.trelloapp.repository;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;

@Repository
public class ActivityRepositoryImpl implements ActivityRepository {
    private static final Logger logger = LogManager.getLogger(ActivityRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public ActivityRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean createActivity(Activity activity) {
        logger.info("about to create the activity for the user");
        try {
            entityManager.persist(activity);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return true;
    }


    public boolean updateActivity(Activity activity, int id) {
        logger.info("about to update the comment of the activity");
        try {
            Query query = entityManager.createQuery("update Activity a set a.comment='" + activity.getComment() + "' where a.id='" + id + "'");
            query.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return true;
    }


    public boolean deleteActivity(int activityId) {
        logger.info("about to delete the activity");
        try {
            Activity activity = entityManager.find(Activity.class, activityId);
            entityManager.remove(activity);
        } catch (Exception e) {
          throw new NotFoundException("");
        }
        return true;
    }
}
