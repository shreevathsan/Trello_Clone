package com.ps.trelloapp.repository;

import com.ps.trelloapp.domain.Task;

public interface TaskRepository {
    public boolean createTask(Task task,int projectId,int userId);

    public Task getTaskAndActivitiesByTaskId(int taskId);

    public boolean updateTask(Task task,int taskId);

    public boolean deleteTask(int taskId);

    public Task getTaskById(int taskId);
}
