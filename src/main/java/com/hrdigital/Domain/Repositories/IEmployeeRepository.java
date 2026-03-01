package com.hrdigital.Domain.Repositories;

import com.hrdigital.Domain.Entities.EmployeeEntity;

import java.util.Optional;

public interface IEmployeeRepository {
    Optional<EmployeeEntity> getById(Long id);

    EmployeeEntity create(EmployeeEntity entity);
}