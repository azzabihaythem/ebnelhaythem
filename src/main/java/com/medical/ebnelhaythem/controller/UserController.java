package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.service.UserService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

@RestController
@AllArgsConstructor
@RequestMapping(value = "/v1")
@Slf4j
public class UserController {

    private UserService userService;

    private PasswordEncoder passwordEncoder;

    /**
     * Create a new user
     * @param user
     * @return
     */
    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> signUp(@RequestBody User user)

    {
        log.debug("Create new user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user =  userService.save(user);
        user.setPassword(null);//hide password for response
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

}
