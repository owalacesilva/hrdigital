package com.hrdigital.Infrastrucure.Hibernate.Repositories;

import com.hrdigital.Domain.Entities.User;
import com.hrdigital.Domain.Repositories.IUserRepository;
import com.hrdigital.Infrastrucure.Hibernate.Models.UserModel;
import jakarta.persistence.EntityManager;
import jakarta.persistence.PersistenceContext;
import jakarta.transaction.Transactional;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Repository
@Transactional
public class UserRepository implements IUserRepository {
    @PersistenceContext
    private EntityManager entityManager;

    private User toDomain(UserModel model) {
        if (model == null) return null;
        User user = new User();
        user.id = model.id;
        user.createdAt = model.createdAt;
        user.updatedAt = model.updatedAt;
        user.username = model.username;
        user.email = model.email;
        user.password = model.password;
        return user;
    }

    private UserModel toModel(User user) {
        if (user == null) return null;
        UserModel model = new UserModel();
        model.id = user.id;
        model.createdAt = user.createdAt;
        model.updatedAt = user.updatedAt;
        model.username = user.username;
        model.email = user.email;
        model.password = user.password;
        return model;
    }

    @Override
    public User save(User user) {
        UserModel model = toModel(user);
        if (model.id == null) {
            entityManager.persist(model);
        } else {
            model = entityManager.merge(model);
        }
        return toDomain(model);
    }

    @Override
    public Optional<User> getById(Long id) {
        UserModel model = entityManager.find(UserModel.class, id);
        return Optional.ofNullable(toDomain(model));
    }

    @Override
    public Optional<User> getByUsername(String username) {
        List<UserModel> result = entityManager.createQuery("SELECT u FROM UserModel u WHERE u.username = :username", UserModel.class)
                .setParameter("username", username)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(toDomain(result.get(0)));
    }

    @Override
    public Optional<User> getByEmail(String email) {
        List<UserModel> result = entityManager.createQuery("SELECT u FROM UserModel u WHERE u.email = :email", UserModel.class)
                .setParameter("email", email)
                .getResultList();
        return result.isEmpty() ? Optional.empty() : Optional.of(toDomain(result.get(0)));
    }

    @Override
    public List<User> findAll() {
        List<UserModel> models = entityManager.createQuery("SELECT u FROM UserModel u", UserModel.class).getResultList();
        return models.stream().map(this::toDomain).collect(Collectors.toList());
    }

    @Override
    public void deleteById(Long id) {
        UserModel model = entityManager.find(UserModel.class, id);
        if (model != null) {
            entityManager.remove(model);
        }
    }
}

