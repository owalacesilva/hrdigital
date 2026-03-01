package com.hrdigital.Infrastrucure.Hibernate.Models;

import com.hrdigital.Domain.Entities.EmployeeEntity;
import jakarta.persistence.*;

import java.time.LocalDateTime;

@Entity
@Table(name = "employees")
public class EmployeeModel {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    @Column(name = "created_at")
    public LocalDateTime createdAt;

    @Column(name = "updated_at")
    public LocalDateTime updatedAt;

    @Column(name = "first_name", nullable = false)
    public String firstName;

    @Column(name = "last_name", nullable = false)
    public String lastName;

    @Column(name = "email", nullable = false, unique = true)
    public String email;

    @PrePersist
    protected void onCreate() {
        this.createdAt = LocalDateTime.now();
        this.updatedAt = LocalDateTime.now();
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public EmployeeEntity toDomain() {
        return new EmployeeEntity(
            this.id,
            this.firstName,
            this.lastName,
            this.email
        );
    }
}
