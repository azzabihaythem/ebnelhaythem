package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.User;

public interface UserDetailsService {
    public User loadUserByUsername(String username);
}
