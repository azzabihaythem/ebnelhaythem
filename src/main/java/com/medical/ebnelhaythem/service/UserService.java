package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;

import java.util.List;
import java.util.Optional;

public interface UserService {

    public List<User> getAllUsers() ;
    public User findByLogin(String login);
    public Optional<User> findById(Long id);
    public User save(User user);
}
