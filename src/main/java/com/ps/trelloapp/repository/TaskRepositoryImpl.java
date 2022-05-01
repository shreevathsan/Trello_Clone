package com.ps.trelloapp.repository;

import com.ps.trelloapp.ExceptionHandler.NotFoundException;
import com.ps.trelloapp.domain.Task;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.Query;
import java.util.List;

@Repository
public class TaskRepositoryImpl implements TaskRepository {

    private static final Logger logger = LogManager.getLogger(TaskRepositoryImpl.class);

    private final EntityManager entityManager;

    @Autowired
    public TaskRepositoryImpl(EntityManager entityManager) {
        this.entityManager = entityManager;
    }

    public boolean createTask(Task task, int projectId, int userId) {
        logger.info("got the data from the service class to create task for id :" + task.getId());
        try {
            Query query = entityManager.createQuery("select u.userId from UserProject u where u.projectId='" + projectId + "'");
            List<Integer> userList = query.getResultList();
            logger.info("the userList is :::::" + userList);
            logger.info("the given userId is :::::::" + userId);
            if (userList.contains(userId)) {
                entityManager.persist(task);
            } else {
                return false;
            }
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return true;
    }


    public Task getTaskAndActivitiesByTaskId(int taskId) {
        logger.info("got the data from the service class to get the task by passing taskId :" + taskId);
        Task task = null;
        try {
            task = entityManager.find(Task.class, taskId);
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        if (task == null) {
            throw new NotFoundException("");
        }
        return task;

    }

    @Override
    public boolean updateTask(Task task, int taskId) {
        logger.info("got the request from the service class to update the status of the task :" + task.getTitle());
        try {
            Query query = entityManager.createQuery("update Task t set t.status='" + task.getStatus() + "' where t.id='" + taskId + "'");
            query.executeUpdate();
        } catch (Exception e) {
            logger.error(e);
            logger.catching(e);
        }
        return true;
    }

    public boolean deleteTask(int taskId) {
        logger.info("about to delete the task for id :" + taskId);
        try {
            Task task = entityManager.find(Task.class, taskId);
            entityManager.remove(task);
        } catch (Exception e) {
            throw new NotFoundException("");
        }

        return true;

    }

    public Task getTaskById(int taskId) {
        logger.info("got the request from the client to get the task by passing taskId :" + taskId);
        Task task = entityManager.find(Task.class, taskId);
        return task;
    }
}
