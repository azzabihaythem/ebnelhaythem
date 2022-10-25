package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.repository.RoleRepository;
import com.medical.ebnelhaythem.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.Optional;

@Component
public class RoleServiceImpl implements RoleService{

    @Autowired
    private RoleRepository roleRepository;
/*
    @Override
    public List<User> getAllUsers() {
        return (List<User>)User.findAll();
        //List<User> findAll() ;
    }

 */





    @Override
    public Role findById(Long id) {
        return roleRepository.findById( id).get() ;
    }

}
