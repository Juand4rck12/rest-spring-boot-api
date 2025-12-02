package com.api.crud.api_crud.service;

import com.api.crud.api_crud.exceptions.ResourceNotFoundException;
import com.api.crud.api_crud.model.User;
import com.api.crud.api_crud.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserService {
    private final UserRepository userRepository;

    public List<User> listAllUsers() {
        return userRepository.findAll();
    }

    public User getUserById(Long id) {
        return userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));
    }

    public User addUser(User user) {
        return userRepository.save(user);
    }

    public User updateUser(User user, Long id) {
        User userExists = userRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Usuario no encontrado"));

        userExists.setName(user.getName());
        userExists.setEmail(user.getEmail());
        return userRepository.save(userExists);
    }

    public void deleteUser(Long id) {
        userRepository.findById(id)
            .ifPresentOrElse(userRepository::delete, () -> {
                throw new ResourceNotFoundException("Usuario no encontrado");
            });
    }
}
