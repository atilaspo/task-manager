package com.santiago.taskmanager.service;

import com.santiago.taskmanager.dto.ProjectDTO;
import com.santiago.taskmanager.dto.TaskDTO;
import com.santiago.taskmanager.model.Project;
import com.santiago.taskmanager.model.Task;
import com.santiago.taskmanager.model.User;
import com.santiago.taskmanager.repository.ProjectRepository;
import com.santiago.taskmanager.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class ProjectServiceImpl implements ProjectService {

    private final ProjectRepository projectRepository;
    private final UserRepository userRepository;

    public ProjectServiceImpl(ProjectRepository projectRepository, UserRepository userRepository) {
        this.projectRepository = projectRepository;
        this.userRepository = userRepository;
    }

    @Override
    public ProjectDTO createProject(ProjectDTO projectDTO) {
        Project project = new Project();
        project.setName(projectDTO.getName());
        project.setDescription(projectDTO.getDescription());
        project.setStartDate(projectDTO.getStartDate());
        project.setEndDate(projectDTO.getEndDate());
        project.setStatus(projectDTO.getStatus());

        if (projectDTO.getCollaboratorIds() != null) {
            List<User> collaborators = userRepository.findAllById(projectDTO.getCollaboratorIds());
            project.setCollaborators(collaborators);
        }

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

            if (projectDTO.getCollaboratorIds() != null) {
                List<User> collaborators = userRepository.findAllById(projectDTO.getCollaboratorIds());
                project.setCollaborators(collaborators);
            }

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
                .tasks(project.getTasks() != null ?
                        project.getTasks().stream().map(this::convertTaskToDTO).collect(Collectors.toList()) :
                        Collections.emptyList())
                .collaboratorIds(project.getCollaborators() != null ?
                        project.getCollaborators().stream().map(User::getId).collect(Collectors.toList()) :
                        Collections.emptyList())
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
                .projectId(task.getProject().getId())
                .assignedUsersId(task.getAssignedUsers() != null ?
                        task.getAssignedUsers().stream().map(User::getId).collect(Collectors.toList()) :
                        Collections.emptyList()
                )
                .build();
    }
}
