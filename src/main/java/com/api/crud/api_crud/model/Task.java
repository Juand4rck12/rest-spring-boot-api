package com.api.crud.api_crud.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Entity
@Table(name = "tareas")
public class Task {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "proyecto_id", nullable = false)
    @NotNull(message = "El proyecto asociado es obligatorio")
    private Project projectId;

    @Column(name = "titulo", nullable = false)
    @Size(max = 150, message = "El titulo no debe superar los 150 caracteres")
    @NotNull(message = "El titulo es obligatorio")
    private String title;

    @Column(name = "descripcion")
    private String description;

    @Enumerated(EnumType.STRING)
    @Column(name = "estado")
    private Status status;

    @Column(name = "fecha_creacion", insertable = false, updatable = false)
    private LocalDateTime creationDate;
}
