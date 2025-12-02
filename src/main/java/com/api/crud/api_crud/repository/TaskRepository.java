package com.api.crud.api_crud.repository;

import com.api.crud.api_crud.model.Task;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TaskRepository extends JpaRepository<Task, Long> {
}
