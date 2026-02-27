package com.hrdigital.Domain.Repositories;

import com.hrdigital.Domain.Entities.User;

import java.util.List;
import java.util.Optional;

public interface IUserRepository {
    User save(User user);
    Optional<User> getById(Long id);
    Optional<User> getByUsername(String username);
    Optional<User> getByEmail(String email);
    List<User> findAll();
    void deleteById(Long id);
}
