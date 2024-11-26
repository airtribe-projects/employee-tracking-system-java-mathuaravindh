package org.airtribe.employeetracking.controller;

import org.airtribe.employeetracking.dto.ProjectDTO;
import org.airtribe.employeetracking.entity.Project;
import org.airtribe.employeetracking.service.ProjectService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
public class ProjectController {

    @Autowired
    private ProjectService projectService;

    // Get all projects
    @GetMapping("/projects")
    public List<ProjectDTO> getAllProjects() {
        return projectService.getAllProjects();
    }

    // Get a project by ID
    @GetMapping("/projects/{id}")
    public ResponseEntity<Project> getProjectById(@PathVariable int id) {
        return projectService.getProjectById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    // Create a new project
    @PostMapping("/projects")
    public Project createProject(@RequestBody ProjectDTO projectDTO) {
        return projectService.createProject(projectDTO);
    }

    // Update an existing project
    @PutMapping("/projects/{id}")
    public ResponseEntity<Project> updateProject(
            @PathVariable int id,
            @RequestBody Project updatedProject
    ) {
        try {
            return ResponseEntity.ok(projectService.updateProject(id, updatedProject));
        } catch (RuntimeException e) {
            return ResponseEntity.notFound().build();
        }
    }

    // Delete a project
    @DeleteMapping("/projects/{id}")
    public ResponseEntity<Void> deleteProject(@PathVariable int id) {
        projectService.deleteProject(id);
        return ResponseEntity.noContent().build();
    }
}
