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
import org.slf4j.Logger;

@RestController
@RequestMapping(value = "/v1/users")
public class UserController {


    @Autowired
    private UserService userService;

    @Autowired
    private PatientService patientService;

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
    public User signUp(@RequestBody User user)

    {
        log.info("This is a test log signUp");
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
        log.info("This is a test log postPatient");
         patientService.save(patientDto);

        return new ResponseEntity(patientService.save(patientDto), HttpStatus.CREATED);
    }
}
