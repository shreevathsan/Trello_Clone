package com.ps.repository;

import com.ps.domain.*;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.hibernate.SessionFactory;
import org.hibernate.cfg.Configuration;

public class SessionFactoryProvider {

    private static SessionFactory sessionFactory;

    private static final Logger logger = LogManager.getLogger(SessionFactoryProvider.class);

    public static SessionFactory getSessionFactory() {
        if (sessionFactory != null){
            return sessionFactory;
        }
        logger.info("about to create session factory");
        sessionFactory = new Configuration()
                .configure("hibernate.cfg.xml")
                .addAnnotatedClass(User.class)
                .addAnnotatedClass(Project.class)
                .addAnnotatedClass(Task.class)
                .addAnnotatedClass(Activity.class)
                .addAnnotatedClass(UserProject.class)
                .buildSessionFactory();
        return sessionFactory;
    }
}
