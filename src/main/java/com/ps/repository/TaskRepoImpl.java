package com.ps.repository;


import com.ps.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.query.Query;

import java.util.List;

public class TaskRepoImpl implements TaskRepository {
    private static final Logger logger = LogManager.getLogger(TaskRepoImpl.class);

    public String createTask(Task task, int projectId, int userId) {
        logger.info("about to save the data in DB");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("select u.userId from UserProject u where u.projectId='" + projectId + "'");
            List<Integer> userList = query.getResultList();
            if (userList.contains(userId)) {
                session.save(task);
                session.getTransaction().commit();
                return "saved";
            }
            return null;


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
    public Task getTaskById(int taskId) {
        logger.info("about to get the task by passing" + taskId);
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            return task;

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
    public Task getTaskAndActivitiesById(int taskId) {
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            logger.info("*(*(*((*((*" + task);
            session.getTransaction().commit();
            return task;
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            throw new RuntimeException("please check the input");

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
    public void updateStatusOfTheTask(int taskId, String status) {
        logger.info("got the data from the service class");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Task task = session.get(Task.class, taskId);
            task.setStatus(status);
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

    @Override
    public String deleteTask(int id) {
        logger.info("got the request from the service class");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Task task = session.get(Task.class, id);
            logger.info(task);
            if (task != null) {
                session.delete(task);
                session.getTransaction().commit();
                return "deleted";
            }
            return "notDeleted";

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            throw new RuntimeException("task not found are already deleted");
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
