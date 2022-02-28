package com.medical.ebnelhaythem.controller;


import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.dto.PatientDto;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
@RequestMapping(value = "/v1/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

    @Autowired
    private PasswordEncoder passwordEncoder;

/*
    public UserController(UserRepository userRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.userRepository = userRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

 */


    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public User signUp(@RequestBody User user)

    {
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        return userService.save(user);
    }

    /**
     * create patient (with user and prise en charge) endPoint
     * @param patientDto
     * @return
     */
    @PostMapping(path = "/patients", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postPatient(@RequestBody PatientDto patientDto){

         patientService.save(patientDto);

        return new ResponseEntity(patientService.save(patientDto), HttpStatus.CREATED);
    }
}
