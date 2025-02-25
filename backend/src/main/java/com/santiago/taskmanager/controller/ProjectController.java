package com.santiago.taskmanager.controller;

import com.santiago.taskmanager.dto.ProjectDTO;
import com.santiago.taskmanager.service.ProjectService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/projects")
public class ProjectController {

    private final ProjectService projectService;

    public ProjectController(ProjectService projectService) {
        this.projectService = projectService;
    }

    // GET all projects
    @GetMapping
    public List<ProjectDTO> getProjects() {
        return projectService.getAllProjects();
    }

    // GET project by ID
    @GetMapping("/{id}")
    public ResponseEntity<ProjectDTO> getProjectById(@PathVariable Long id) {
        Optional<ProjectDTO> projectById = projectService.getProjectById(id);

        if (projectById.isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectById.get());
    }

    // POST create project
    @PostMapping
    public ResponseEntity<ProjectDTO> createProject(@RequestBody ProjectDTO projectDTO) {
        if (projectDTO == null || projectDTO.getName() == null || projectDTO.getStartDate() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(projectService.createProject(projectDTO));
    }

    // PUT update project
    @PutMapping("/{id}")
    public ResponseEntity<ProjectDTO> updateProject(@PathVariable Long id, @RequestBody ProjectDTO projectDTO) {
        if (projectDTO == null || projectDTO.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(projectService.updateProject(id, projectDTO));
    }

    // DELETE project
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable Long id) {
        if (projectService.getProjectById(id).isEmpty()) {
            return ResponseEntity.notFound().build();
        }
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }

}
