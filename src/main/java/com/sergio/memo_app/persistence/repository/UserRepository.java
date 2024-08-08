package com.sergio.memo_app.persistence.repository;

import com.sergio.memo_app.persistence.entity.User;
import org.springframework.data.repository.ListCrudRepository;

import java.util.Optional;

public interface UserRepository extends ListCrudRepository<User, Integer> {
    Optional<User> findByUsername(String username);
}
