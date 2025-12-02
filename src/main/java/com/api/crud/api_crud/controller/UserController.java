package com.api.crud.api_crud.controller;

import com.api.crud.api_crud.exceptions.AlreadyExistsException;
import com.api.crud.api_crud.exceptions.ResourceNotFoundException;
import com.api.crud.api_crud.model.User;
import com.api.crud.api_crud.response.ApiResponse;
import com.api.crud.api_crud.service.UserService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.*;

@RestController
@RequestMapping("${api.prefix}/users")
@RequiredArgsConstructor
@CrossOrigin("*")
public class UserController {
    private final UserService userService;

    @GetMapping
    public ResponseEntity<ApiResponse> getAllUsers() {
        try {
            List<User> users = userService.listAllUsers();
            return ResponseEntity.ok(new ApiResponse("Listado de usuarios:", users));
        } catch (Exception e) {
            return ResponseEntity.status(INTERNAL_SERVER_ERROR)
                    .body(new ApiResponse("Ocurrio un error al listar los usuarios:", e.getMessage()));
        }
    }

    @GetMapping("/user/{id}")
    public ResponseEntity<ApiResponse> getUserById(@PathVariable Long id) {
        try {
            User userExists = userService.getUserById(id);
            return ResponseEntity.ok(new ApiResponse("Usuario encontrado:", userExists));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @PostMapping("/user/add")
    public ResponseEntity<ApiResponse> addUser(@Valid @RequestBody User user) {
        try {
            User newUser = userService.addUser(user);
            return ResponseEntity.status(CREATED).body(new ApiResponse("Usuario creado:", newUser));
        } catch (AlreadyExistsException e) {
            return ResponseEntity.status(CONFLICT).
                    body(new ApiResponse("Error al crear el usuario", e.getMessage()));
        }
    }

    @PutMapping("/user/update/{id}")
    public ResponseEntity<ApiResponse> updateUser(@Valid @RequestBody User user, @PathVariable Long id) {
        try {
            User userExists = userService.updateUser(user, id);
            return ResponseEntity.ok(new ApiResponse("Usuario actualizado", userExists));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }

    @DeleteMapping("/user/delete/{id}")
    public ResponseEntity<ApiResponse> deleteUser(@PathVariable Long id) {
        try {
            userService.deleteUser(id);
            return ResponseEntity.ok(new ApiResponse("Usuario eliminado con exito", null));
        } catch (ResourceNotFoundException e) {
            return ResponseEntity.status(NOT_FOUND).body(new ApiResponse(e.getMessage(), null));
        }
    }
}
