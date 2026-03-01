package com.hrdigital.Application.Controllers.Employee;

import com.hrdigital.Application.Dto.CreateEmployeeDto;
import com.hrdigital.Domain.Entities.EmployeeEntity;
import com.hrdigital.Domain.Repositories.IEmployeeRepository;
import com.hrdigital.Infrastrucure.Hibernate.Repositories.EmployeeRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class CreateEmployeeController {
    private static final Logger logger = LoggerFactory.getLogger(CreateEmployeeController.class);
    private final IEmployeeRepository repository;

    public CreateEmployeeController(EmployeeRepository repository) {
        this.repository = repository;
    }

    @PostMapping(
        name = "createEmployee",
        path = "/employees"
    )
    public ResponseEntity<String> postEmployee(
        @RequestBody(required = true) CreateEmployeeDto request
    ) {
        logger.debug("Create Employee request received");

        System.out.println("Request: " + request.getFirstName() + " " + request.getLastName() + " " + request.getEmail());

        EmployeeEntity employee = new EmployeeEntity(
            null,
            request.getFirstName(),
            request.getLastName(),
            request.getEmail()
        );
        this.repository.create(employee);

        return ResponseEntity.ok("Endpoint to create employee is working");
    }
}
