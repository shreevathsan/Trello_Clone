package com.ps.repository;

import com.ps.domain.Task;

public interface TaskRepository {
    String createTask(Task task,int projectId,int userId);
    Task getTaskById(int taskId);
    Task getTaskAndActivitiesById(int taskId);
    void updateStatusOfTheTask(int taskId,String status);
    String deleteTask(int id);
}
