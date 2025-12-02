package com.api.crud.api_crud.controller;

import com.api.crud.api_crud.exceptions.AlreadyExistsException;
import com.api.crud.api_crud.exceptions.ResourceNotFoundException;
import com.api.crud.api_crud.model.Project;
import com.api.crud.api_crud.response.ApiResponse;
import com.api.crud.api_crud.service.ProjectService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/projects")
@RequiredArgsConstructor
@CrossOrigin("*")
public class ProjectController {
    private final ProjectService projectService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllProjects() {
        try {
            List<Project> projects = projectService.listAllProjects();
            return ResponseEntity.ok(new ApiResponse("Listado de proyectos:", projects));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error al obtener los proyectos:", e.getMessage()));
        }
    }

    @GetMapping("/project/{id}")
    public ResponseEntity<ApiResponse> getProjectById(@PathVariable Long id) {
        try {
            Project projectExists = projectService.getProjectById(id);
            return ResponseEntity.ok(new ApiResponse("Proyecto encontrada:", projectExists));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/project/add")
    public ResponseEntity<ApiResponse> addProject(@RequestBody Project project) {
        try {
            Project newProject = projectService.addProject(project);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Proyecto creado:", newProject));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Error al crear el proyecto", e.getMessage()));
        }
    }

    @PutMapping("/project/update/{id}")
    public ResponseEntity<ApiResponse> updateProject(@PathVariable Long id, @RequestBody Project project) {
        try {
            Project updatedProject = projectService.updateProject(project, id);
            return ResponseEntity.ok(new ApiResponse("Proyecto actualizado:", updatedProject));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/project/delete/{id}")
    public ResponseEntity<ApiResponse> deleteProject(@PathVariable Long id) {
        try {
            projectService.deleteProject(id);
            return ResponseEntity.ok(new ApiResponse("Proyecto eliminado", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
