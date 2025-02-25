package com.santiago.taskmanager.service;

import com.santiago.taskmanager.dto.ProjectDTO;

import java.util.List;
import java.util.Optional;

public interface
ProjectService {
    ProjectDTO createProject(ProjectDTO projectDTO);
    List<ProjectDTO> getAllProjects();
    Optional<ProjectDTO> getProjectById(Long id);
    ProjectDTO updateProject(Long id, ProjectDTO projectDTO);
    void deleteProject(Long id);
}
