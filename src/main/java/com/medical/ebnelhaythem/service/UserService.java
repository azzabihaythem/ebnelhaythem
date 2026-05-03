package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;

import java.util.Optional;

public interface UserService {

    public User findByLogin(String login);
    public void deleteById(Long id);
    public Optional<User> findById(Long id);
    public User save(User user);
}
