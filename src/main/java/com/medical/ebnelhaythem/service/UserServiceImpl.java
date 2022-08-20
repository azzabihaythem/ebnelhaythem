package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class UserServiceImpl implements UserService{

    @Autowired
    private UserRepository userRepository;
/*
    @Override
    public List<User> getAllUsers() {
        return (List<User>)User.findAll();
        //List<User> findAll() ;
    }

 */

    @Override
    public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    @Override
    public User findByLogin(String login) {
        return userRepository.findByLogin(login);
    }

    @Override
    public Optional<User> findById(Long id) {
        return userRepository.findById(id);
    }

    @Override
    public User save(User user) {
       return userRepository.save(user);
    }
}
