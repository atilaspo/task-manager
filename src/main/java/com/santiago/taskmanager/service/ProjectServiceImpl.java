package com.santiago.taskmanager.service;

import com.santiago.taskmanager.dto.ProjectDTO;
import com.santiago.taskmanager.dto.TaskDTO;
import com.santiago.taskmanager.model.Project;
import com.santiago.taskmanager.model.Task;
import com.santiago.taskmanager.repository.ProjectRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository) {
        this.projectRepository = projectRepository;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus(projectDTO.getStatus());

        List<Task> tasks = (projectDTO.getTasks() != null) ? projectDTO.getTasks().stream()
                .map(taskDTO -> {
                    Task task = new Task();
                    task.setTitle(taskDTO.getTitle());
                    task.setDescription(taskDTO.getDescription());
                    task.setDueDate(taskDTO.getDueDate());
                    task.setStatus(taskDTO.getStatus());
                    task.setPriority(taskDTO.getPriority());
                    task.setProject(project);  // Asocia la tarea al proyecto
                    return task;
                }).collect(Collectors.toList()) : Collections.emptyList(); // Si es null, asignar una lista vacía

        project.setTasks(tasks);

        Project savedProject = projectRepository.save(project);
        return convertToDTO(savedProject);
    }


    @Override
    public List<ProjectDTO> getAllProjects() {
        return projectRepository.findAll().stream().map(this::convertToDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<ProjectDTO> getProjectById(Long id) {
        return projectRepository.findById(id).map(this::convertToDTO);
    }

    @Override
    public ProjectDTO updateProject(Long id, ProjectDTO projectDTO) {
        return projectRepository.findById(id).map(project -> {
            project.setName(projectDTO.getName());
            project.setDescription(projectDTO.getDescription());
            project.setStartDate(projectDTO.getStartDate());
            project.setEndDate(projectDTO.getEndDate());
            project.setStatus(projectDTO.getStatus());

            Project updatedProject = projectRepository.save(project);
            return convertToDTO(updatedProject);
        }).orElseThrow(() -> new RuntimeException("Project not found"));
    }

    @Override
    public void deleteProject(Long id) {
        projectRepository.deleteById(id);
    }

    private ProjectDTO convertToDTO(Project project) {
        return ProjectDTO.builder()
                .id(project.getId())
                .name(project.getName())
                .description(project.getDescription())
                .startDate(project.getStartDate())
                .endDate(project.getEndDate())
                .status(project.getStatus())
                .tasks(project.getTasks() != null ? project.getTasks().stream()
                        .map(this::convertTaskToDTO)
                        .collect(Collectors.toList()) : Collections.emptyList())
                .build();
    }

    private TaskDTO convertTaskToDTO(Task task) {
        return TaskDTO.builder()
                .id(task.getId())
                .title(task.getTitle())
                .description(task.getDescription())
                .dueDate(task.getDueDate())
                .status(task.getStatus())
                .priority(task.getPriority())
                .projectId(task.getProject() != null ? task.getProject().getId() : null)
                .build();
    }
}
