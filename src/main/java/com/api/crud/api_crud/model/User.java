package com.api.crud.api_crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "usuarios")
public class User {
    // Por tiempo de desarrollo no se usara DTO
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nombre", nullable = false)
    @Size(max = 100, message = "El nombre debe tener máximo 100 caracteres")
    @NotNull(message = "El nombre es obligatorio")
    private String name;

    @Column(name = "correo", nullable = false, unique = true)
    @Email(message = "El campo debe ser un email valido")
    @NotNull(message = "El campo correo es obligatorio")
    private String email;
}
