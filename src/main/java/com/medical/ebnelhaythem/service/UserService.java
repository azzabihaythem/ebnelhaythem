package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;

public interface UserService {

    public User findByLogin(String login);

    public User save(User user);
}
