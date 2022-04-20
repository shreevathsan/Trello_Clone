package com.ps.repository;

import com.ps.domain.Activity;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

public class ActivityRepoImpl implements ActivityRepository {
    private static final Logger logger = LogManager.getLogger(ActivityRepoImpl.class);

    @Override
    public void createActivity(Activity activity) {
        logger.info("about to save the activity in DB");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(activity);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            throw new RuntimeException();
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void updateActivity(int id, String comment) {
        logger.info("received data from the service class" + id);
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Activity activity = session.get(Activity.class, id);
            activity.setComment(comment);
            session.getTransaction().commit();

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        } finally {
            try {
                if (session != null) {
                    session.close();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }
}
