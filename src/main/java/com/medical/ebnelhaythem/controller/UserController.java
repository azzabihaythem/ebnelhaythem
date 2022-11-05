package com.medical.ebnelhaythem.controller;


import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.dto.PatientDto;
import com.medical.ebnelhaythem.service.CliniqueService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.RoleService;
import com.medical.ebnelhaythem.service.UserService;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.persistence.Id;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(value = "/v1/users")
@CrossOrigin("*")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private RoleService roleService;

    @Autowired
    private CliniqueService cliniqueService;

    @Autowired
    private PasswordEncoder passwordEncoder;


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(UserController.class);

    /**
     * Create a new user
     * @param user
     * @return
     */
    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody User user,@RequestParam Long roleId)
    {
        log.debug("Create new user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));

        List<Role>  roleList = new ArrayList<>()  ;
      //  User userid = userService.findById(roleid).get();
       roleList.add(roleService.findById(roleId)) ;
         user.setRoles(roleList);
         user =  userService.save(user);
        user.setPassword(null);//hide password for response
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    /**
     *
     * USER CRUD
     */
    @GetMapping(path = "/listUser")
    public List<User> getAllUsers()
        {
        List<User> listUsers =userService.getAllUsers();
        return (listUsers) ;
    }
    /**
     * Create a new clinique
     * @param clinique
     * @return
     */
    @PostMapping(path = "/clinique", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postClinique(@RequestBody Clinique clinique)

    {   log.debug("Create new clinique");
        return new ResponseEntity(cliniqueService.save(clinique), HttpStatus.CREATED);
    }










}
