package com.example.demo.service;

import com.example.demo.model.User;

import java.util.Optional;

public interface IUserService {
    Optional<User> findByUsername(String name);

    Boolean existsByUsername( String userName);

    Boolean existsByEmail(String email);

    User save(User user);
}
