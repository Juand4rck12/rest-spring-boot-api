package com.api.crud.api_crud.service;

import com.api.crud.api_crud.exceptions.ResourceNotFoundException;
import com.api.crud.api_crud.model.Task;
import com.api.crud.api_crud.repository.TaskRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TaskService {
    private final TaskRepository taskRepository;

    public List<Task> listAllTasks() {
        return taskRepository.findAll();
    }

    public Task getTaskById(Long id) {
        return taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));
    }

    public Task addTask(Task task) {
        return taskRepository.save(task);
    }

    public Task updateTask(Task task, Long id) {
        Task taskExists = taskRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Tarea no encontrada"));

        taskExists.setTitle(task.getTitle());
        taskExists.setStatus(task.getStatus());
        taskExists.setDescription(task.getDescription());
        taskExists.setProjectId(task.getProjectId());

        return taskRepository.save(taskExists);
    }

    public void deleteTask(Long id) {
        taskRepository.findById(id)
            .ifPresentOrElse(taskRepository::delete, () -> {
                throw new ResourceNotFoundException("Tarea no encontrada");
            });
    }
}
