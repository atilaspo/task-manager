package com.santiago.taskmanager.controller;

import com.santiago.taskmanager.model.Project;
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
    public List<Project> getProjects() {
        return projectService.getAllProjects();
    }

    // GET project by ID
    @GetMapping("/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable Long id) {
        Optional<Project> projectById = projectService.getProjectById(id);

        if (projectById.isEmpty()){
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(projectById.get());
    }

    // POST create project
    @PostMapping
    public ResponseEntity<Project> createProject(@RequestBody Project project) {
        if (project == null || project.getName() == null || project.getStartDate() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(projectService.createProject(project));
    }

    // PUT update project
    @PutMapping("/{id}")
    public ResponseEntity<Project> updateProject(@PathVariable Long id, @RequestBody Project project) {
        if (project == null || project.getName() == null) {
            return ResponseEntity.badRequest().build();
        }
        return ResponseEntity.ok(projectService.updateProject(id, project));
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
