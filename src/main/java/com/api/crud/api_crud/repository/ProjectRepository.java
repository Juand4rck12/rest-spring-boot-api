package com.api.crud.api_crud.repository;

import com.api.crud.api_crud.model.Project;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProjectRepository extends JpaRepository<Project, Long> {
}
