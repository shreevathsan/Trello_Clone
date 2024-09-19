package com.ps.trelloapp.repository;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.Project;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;


@Repository
public class ProjectRepositoryImpl implements ProjectRepository {
    private static final Logger logger = LogManager.getLogger(ProjectRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public ProjectRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean createProject(Project project) {
        logger.info("about to create the project with name :" + project.getName());
        try {
            entityManager.persist(project);

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
            throw new RuntimeException("Project Not Saved");
        }
        return true;
    }


    public Project getProjectAndTaskByProjectId(int projectId) {
        logger.info("about to get the projects and tasks associated with it passing projectId :" + projectId);
        Project project = null;
        try {
            project = entityManager.find(Project.class, projectId);

        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        if (project != null) {
            return project;
        } else {
            throw new NotFoundException("");
        }
    }


    public void updateProject(Project project) {
        logger.info("got the request from the service class to update project name for project :" + project.getTasks());
        Query query = entityManager.createQuery("update Project p set p.name='" + project.getName() + "' where p.id='" + project.getId() + "'");
        query.executeUpdate();

    }

    public void deleteProject(int id) {
        try {
            logger.info("got the request from the client to delete project for id :" + id);
            Project project = entityManager.getReference(Project.class, id);
            entityManager.remove(project);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }

    }

    @Override
    public Project getProjectById(int projectId) {
        logger.info("got the request from the user to get the project for Id :" + projectId);
        Project project=null;
        try {
             project=entityManager.find(Project.class, projectId);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        if(project==null)
        {
            throw new NotFoundException("");
        }
        return project;
    }
}
