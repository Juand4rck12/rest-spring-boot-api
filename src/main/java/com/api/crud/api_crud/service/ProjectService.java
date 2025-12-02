package com.api.crud.api_crud.service;

import com.api.crud.api_crud.exceptions.ResourceNotFoundException;
import com.api.crud.api_crud.model.Project;
import com.api.crud.api_crud.repository.ProjectRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ProjectService {
    private final ProjectRepository projectRepository;

    public List<Project> listAllProjects() {
        return projectRepository.findAll();
    }

    public Project getProjectById(Long id) {
        return projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));
    }

    public Project addProject(Project project) {
        return projectRepository.save(project);
    }

    public Project updateProject(Project project, Long id) {
        Project existsProject = projectRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Proyecto no encontrado"));

        existsProject.setName(project.getName());
        existsProject.setDescription(project.getDescription());
        existsProject.setUserId(project.getUserId());

        return projectRepository.save(existsProject);
    }

    public void deleteProject(Long id) {
        projectRepository.findById(id)
            .ifPresentOrElse(projectRepository::delete, () -> {
                throw new ResourceNotFoundException("Proyecto no encontrado");
            });
    }
}
