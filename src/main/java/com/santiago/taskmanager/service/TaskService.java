package com.santiago.taskmanager.service;

import com.santiago.taskmanager.model.Task;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    Task createTask(Task task);
    List<Task> getAllTasks();
    Optional<Task> getTaskById(Long id);
    Task updateTask(Long id, Task task);
    void deleteTask(Long id);
}
