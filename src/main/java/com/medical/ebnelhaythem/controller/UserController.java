package com.medical.ebnelhaythem.controller;


import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.dto.PatientDto;
import com.medical.ebnelhaythem.service.CliniqueService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.UserService;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

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
    public ResponseEntity<?> signUp(@RequestBody User user)

    {
        log.debug("Create new user");
        user.setPassword(passwordEncoder.encode(user.getPassword()));
        user =  userService.save(user);
        user.setPassword(null);//hide password for response
        return new ResponseEntity(user, HttpStatus.CREATED);
    }

    /**
     *
     * USER CRUD
     */
    @GetMapping(path = "/listuser")
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

    /**
     * create patient (with user and prise en charge) endPoint
     * @param patient
     * @return
     */
    @PostMapping(path = "/patients", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postPatient(@RequestBody Patient patient){
        log.debug("Create new patient");
        patient.setUser( userService.save(patient.getUser()));
        return new ResponseEntity(patientService.save(patient), HttpStatus.CREATED);
    }


    /**
     * update patient (with user and prise en charge) endPoint
     * @param patient
     * @return
     */
    @PutMapping(path = "/patients", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePatient(@RequestBody Patient patient){
        log.debug("update patient");
        return new ResponseEntity(patientService.save(patient), HttpStatus.OK);
    }

    /**
     * get patient  endPoint
     * @param id Patient
     * @return
     */
    @GetMapping(path = "/patients/{id}")
    public ResponseEntity<?> getPatient(@PathVariable("id") Long id){
        log.debug("get patient");
        return new ResponseEntity(patientService.findById(id), HttpStatus.OK);
    }

    /**
     * delete patient  endPoint
     * @param id Patient
     * @return
     */
    @DeleteMapping(path = "/patients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") Long id){
        log.debug("delete patient");
        patientService.deleteById(id);
        return new ResponseEntity( HttpStatus.OK);
    }

    /**
     * desactivate patient  endPoint
     * @param id Patient
     * @return
     */
    @PutMapping(path = "/desactivate/patients/{id}")
    public ResponseEntity<?> desactivatePatient(@PathVariable("id") Long id){
        log.debug("delete patient");
        return new ResponseEntity(patientService.desactivatePatient(id), HttpStatus.OK);
    }

}
