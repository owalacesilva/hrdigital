package com.hrdigital.Infrastrucure.Hibernate.Repositories;

import com.hrdigital.Domain.Entities.EmployeeEntity;
import com.hrdigital.Domain.Repositories.IEmployeeRepository;
import com.hrdigital.Infrastrucure.Hibernate.Models.EmployeeModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.jpa.repository.JpaContext;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Repository
public class EmployeeRepository implements IEmployeeRepository {
    @PersistenceContext
    private final EntityManager em;

    @Autowired
    public EmployeeRepository(JpaContext context) {
        this.em = context.getEntityManagerByManagedType(EmployeeModel.class);
    }

    public Optional<EmployeeEntity> getById(Long id) {
        EmployeeModel model = this.em.find(EmployeeModel.class, id);
        if (model == null) {
            return Optional.empty();
        }

        return Optional.ofNullable(model.toDomain());
    }

    @Transactional
    public EmployeeEntity create(EmployeeEntity entity) {
        EmployeeModel model = new EmployeeModel();
        model.firstName = entity.getFirstName();
        model.lastName = entity.getLastName();
        model.email = entity.getEmail();

        this.em.persist(model);
        this.em.flush();

        return model.toDomain();
    }
}
