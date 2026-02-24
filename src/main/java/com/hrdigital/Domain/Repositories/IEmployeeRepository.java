package com.hrdigital.Domain.Repositories;

import com.hrdigital.Infrastrucure.Hibernate.Models.EmployeeModel;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface IEmployeeRepository extends JpaRepository<EmployeeModel, Long> {

    Optional<EmployeeModel> getByEmail(String email);

    @Query("SELECT e FROM EmployeeModel e WHERE e.firstName LIKE %:name% OR e.lastName LIKE %:name%")
    List<EmployeeModel> findByFullNameContaining(@Param("name") String name);
}

