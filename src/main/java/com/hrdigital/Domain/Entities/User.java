package com.hrdigital.Domain.Entities;

import java.time.LocalDateTime;

public class User {
    public Long id;
    public LocalDateTime createdAt;
    public LocalDateTime updatedAt;
    public String username;
    public String email;
    public String password;

    public User() {}

    // Optionally, add constructors, getters, setters, etc.
}

