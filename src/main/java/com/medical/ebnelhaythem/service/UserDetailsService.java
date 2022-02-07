package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;
import org.springframework.stereotype.Component;

@Component
public interface UserDetailsService {
    public User loadUserByUsername(String username);
}
