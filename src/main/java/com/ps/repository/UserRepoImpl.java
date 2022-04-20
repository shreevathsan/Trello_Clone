package com.ps.repository;

import com.ps.domain.Project;
import com.ps.domain.User;
import com.ps.domain.UserProject;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.Session;
import org.hibernate.SessionFactory;

import javax.persistence.Query;
import java.util.List;


public class UserRepoImpl implements UserRepository {
    private static final Logger logger = LogManager.getLogger(UserRepoImpl.class);

    @Override
    public void createUser(User user) {
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(user);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.catching(e);
            throw new RuntimeException("Error creating user");
        }
    }

    @Override
    public User findById(int userId) {
        User user = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            Session session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            user = session.get(User.class, userId);
            session.getTransaction().commit();
            session.close();
        } catch (Exception e) {
            logger.error("Error getting by id: " + userId);
            logger.catching(e);
            throw new RuntimeException("Error getting user by id: " + userId);
        }
        return user;
    }

    @Override
    public void assignProjectForUser(UserProject userProject) {
        logger.info("got the data from the service class");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            session.save(userProject);
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
    public List<Project> getProjectsByUserId(int userId) {
        logger.info("about to get the list of projects for the user");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("select p.name from Project p join UserProject u on p.id=u.projectId where u.userId='" + userId + "'");
            List<Project> projects = query.getResultList();
            return projects;
        } catch (Exception e) {
            throw new RuntimeException("Error getting projects for user: " + userId);
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
    public List<User> getListOfUserByProjectId(int projectId) {
        logger.info("got the data from the service class");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            Query query = session.createQuery("select u.name from User u join UserProject p on u.id=p.userId where p.projectId='" + projectId + "'");
            List<User> userList = query.getResultList();
            session.getTransaction().commit();
            return userList;
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
    public void updateUserById(int userId,String name) {
        logger.info("got the data from the service class");
        Session session = null;
        try {
            SessionFactory sessionFactory = SessionFactoryProvider.getSessionFactory();
            session = sessionFactory.getCurrentSession();
            session.beginTransaction();
            User user = session.get(User.class, userId);
            user.setName(name);
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


}
