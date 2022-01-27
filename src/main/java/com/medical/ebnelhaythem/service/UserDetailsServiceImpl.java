package com.medical.ebnelhaythem.service;


import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.repository.UserRepository;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import java.util.Collections;

@Component
public class UserDetailsServiceImpl implements UserDetailsService
{
    private UserRepository userRepository;

    public UserDetailsServiceImpl(UserRepository userRepository)
    {
        this.userRepository = userRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String login) throws UsernameNotFoundException
    {
        User user = userRepository.findByLogin(login);
        if(user == null)
        {
            throw new UsernameNotFoundException(login);
        }
        return new org.springframework.security.core.userdetails.User(user.getLogin(), user.getPassword(), Collections.emptyList());
    }
}