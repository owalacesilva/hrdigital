package com.hrdigital.Infrastrucure.Hibernate.Models;

import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "users")
public class UserModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @Column(name = "username", nullable = false, unique = true)
    public String username;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @Column(name = "password", nullable = false)
    public String password;

    public UserModel() {}

    // Getters and setters can be added here
}
