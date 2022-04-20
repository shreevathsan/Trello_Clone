package com.ps.repository;

import com.ps.domain.Activity;
import com.ps.domain.Project;
import com.ps.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.HibernateException;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import java.util.List;
import java.util.Objects;

public class ProjectRepoImpl implements ProjectRepository {
    private static final Logger logger = LogManager.getLogger(ProjectRepoImpl.class);

    public void createProject(Project project) {
        logger.info("got the data from the service class");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(project);
            session.getTransaction().commit();
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
    public Project getProjectById(int projectId) {
        logger.info("about to get the project by passing its id");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Project project = session.get(Project.class, projectId);
            var tasks = project.getTasks();
            System.out.println("project: "+ project);
            for(Task t: tasks){
                System.out.println("task:"+ t);
                List<Activity> activities = t.getActivities();
                for(Activity activity: activities){
                    System.out.println(activity.getComment());
                }
            }
            session.getTransaction().commit();
            logger.info("got project from DB: "+ project);
            return project;
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
    public Project getProjectAndTaskByProjectId(int projectId) {
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Project project = session.get(Project.class, projectId);

            var tasks = project.getTasks();
            System.out.println("project: "+ project);
            for(Task t: tasks){
                System.out.println("task:"+ t);
                List<Activity> activities = t.getActivities();
                for(Activity activity: activities){
                    System.out.println(activity.getComment());
                }
            }

            session.getTransaction().commit();
            return project;

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
        return null;
    }

    @Override
    public String deleteProject(int id) {
        logger.info("about to delete the project");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Project project = session.get(Project.class, id);
            logger.info(project);
            if (project != null) {
                session.delete(project);
                session.getTransaction().commit();
                return "deleted";
            }


        } catch (HibernateException e) {
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
        return null;
    }

    @Override
    public void updateProjectName(int id, String name) {
        logger.info("about to update the name of the project");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Project project = session.get(Project.class, id);
            project.setName(name);
            logger.info("************????????" + project);
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
