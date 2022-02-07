package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }
}
