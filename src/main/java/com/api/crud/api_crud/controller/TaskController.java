package com.api.crud.api_crud.controller;

import com.api.crud.api_crud.exceptions.AlreadyExistsException;
import com.api.crud.api_crud.exceptions.ResourceNotFoundException;
import com.api.crud.api_crud.model.Task;
import com.api.crud.api_crud.response.ApiResponse;
import com.api.crud.api_crud.service.TaskService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/tasks")
@RequiredArgsConstructor
@CrossOrigin("*")
public class TaskController {
    private final TaskService taskService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllTasks() {
        try {
            List<Task> tasks = taskService.listAllTasks();
            return ResponseEntity.ok(new ApiResponse("Listado de tareas", tasks));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Error al obtener las tareas", e.getMessage()));
        }
    }

    @GetMapping("/task/{id}")
    public ResponseEntity<ApiResponse> getTaskById(@PathVariable Long id) {
        try {
            Task taskExists = taskService.getTaskById(id);
            return ResponseEntity.ok(new ApiResponse("Tarea encontrada", taskExists));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/task/add")
    public ResponseEntity<ApiResponse> addTask(@Valid @RequestBody Task task) {
        try {
            Task newTask = taskService.addTask(task);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Tarea creada exitosamente", newTask));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).body(new ApiResponse("Ocurrio un error al insertar la tarea", e.getMessage()));
        }
    }

    @PutMapping("/task/update/{id}")
    public ResponseEntity<ApiResponse> updateTask(@Valid @RequestBody Task task, @PathVariable Long id) {
        try {
            Task taskExits = taskService.updateTask(task, id);
            return ResponseEntity.ok(new ApiResponse("Tarea actualizada exitosamente", taskExits));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/task/delete/{id}")
    public ResponseEntity<ApiResponse> deleteTask(@PathVariable Long id) {
        try {
            taskService.deleteTask(id);
            return ResponseEntity.ok(new ApiResponse("Tarea eliminada exitosamente", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
