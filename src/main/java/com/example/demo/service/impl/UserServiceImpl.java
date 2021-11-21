package com.example.demo.service.impl;

import com.example.demo.model.User;
import com.example.demo.repository.IUserRepository;
import com.example.demo.service.IUserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserServiceImpl implements IUserService {
    @Autowired
    private IUserRepository iUserRepository;

    @Override
    public Optional<User> findByUsername(String name) {
        return iUserRepository.findByUsername(name);
    }

    @Override
    public Boolean existsByUsername(String userName) {
        return iUserRepository.existsByUsername(userName);
    }

    @Override
    public Boolean existsByEmail(String email) {
        return iUserRepository.existsByEmail(email);
    }

    @Override
    public User save(User user) {
        return iUserRepository.save(user);
    }
}
