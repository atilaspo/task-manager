package com.santiago.taskmanager.service;

import com.santiago.taskmanager.dto.TaskDTO;

import java.util.List;
import java.util.Optional;

public interface TaskService {
    TaskDTO createTask(TaskDTO taskDTO);
    List<TaskDTO> getAllTasks();
    Optional<TaskDTO> getTaskById(Long id);
    TaskDTO updateTask(Long id, TaskDTO taskDTO);
    void deleteTask(Long id);
}
