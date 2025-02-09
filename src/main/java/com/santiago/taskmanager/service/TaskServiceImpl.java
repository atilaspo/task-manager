package com.santiago.taskmanager.service;

import com.santiago.taskmanager.dto.TaskDTO;
import com.santiago.taskmanager.model.Project;
import com.santiago.taskmanager.model.Task;
import com.santiago.taskmanager.model.User;
import com.santiago.taskmanager.repository.ProjectRepository;
import com.santiago.taskmanager.repository.TaskRepository;
import com.santiago.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;
    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public TaskServiceImpl(TaskRepository taskRepository, ProjectRepository projectRepository, UserRepository userRepository) {
        this.taskRepository = taskRepository;
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public TaskDTO createTask(TaskDTO taskDTO) {
        Task task = new Task();
        task.setTitle(taskDTO.getTitle());
        task.setDescription(taskDTO.getDescription());
        task.setDueDate(taskDTO.getDueDate());
        task.setStatus(taskDTO.getStatus());
        task.setPriority(taskDTO.getPriority());

        if (taskDTO.getProjectId() != null) {
            Project project = projectRepository.findById(taskDTO.getProjectId())
                    .orElseThrow(() -> new RuntimeException("Project not found"));
            task.setProject(project);
        }

        if (taskDTO.getAssignedUsersId() != null && !taskDTO.getAssignedUsersId().isEmpty()) {
            List<User> assignedUsers = userRepository.findAllById(taskDTO.getAssignedUsersId());
            task.setAssignedUsers(assignedUsers);
        }

        Task savedTask = taskRepository.save(task);
        return convertToDTO(savedTask);
    }


    @Override
    public List<TaskDTO> getAllTasks() {
        return taskRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<TaskDTO> getTaskById(Long id) {
        return taskRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public TaskDTO updateTask(Long id, TaskDTO taskDTO) {
        return taskRepository.findById(id).map(task -> {
            task.setTitle(taskDTO.getTitle());
            task.setDescription(taskDTO.getDescription());
            task.setDueDate(taskDTO.getDueDate());
            task.setStatus(taskDTO.getStatus());
            task.setPriority(taskDTO.getPriority());

            if (taskDTO.getProjectId() != null) {
                Project project = projectRepository.findById(taskDTO.getProjectId())
                        .orElseThrow(() -> new RuntimeException("Project not found"));
                task.setProject(project);
            }

            if (taskDTO.getAssignedUsersId() != null && !taskDTO.getAssignedUsersId().isEmpty()) {
                List<User> assignedUsers = userRepository.findAllById(taskDTO.getAssignedUsersId());
                task.setAssignedUsers(assignedUsers);
            }

            Task updatedTask = taskRepository.save(task);
            return convertToDTO(updatedTask);
        }).orElseThrow(() -> new RuntimeException("Task not found"));
    }

    @Override
    public void deleteTask(Long id) {
        taskRepository.deleteById(id);
    }

    private TaskDTO convertToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .assignedUsersId(task.getAssignedUsers() != null ?
                        task.getAssignedUsers().stream().map(User::getId).collect(Collectors.toList())
                        : Collections.emptyList()
                )
                .build();
    }
}