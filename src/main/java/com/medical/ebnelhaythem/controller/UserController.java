package com.medical.ebnelhaythem.controller;


import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.dto.PatientDto;
import com.medical.ebnelhaythem.repository.UserRepository;
import com.medical.ebnelhaythem.service.CliniqueService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.RoleService;
import com.medical.ebnelhaythem.service.UserService;
import org.mapstruct.ap.shaded.freemarker.ext.beans._BeansAPI;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;
import org.slf4j.Logger;

import javax.persistence.Id;
import javax.websocket.server.PathParam;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ExecutionException;

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
    @PreAuthorize("permitAll()")
    @CrossOrigin("*")
    @PostMapping(path = "/signup", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    //public ResponseEntity<?> signUp(@RequestBody User user,@RequestParam Long roleId)
    public ResponseEntity<?> signUp(@RequestBody User user)throws Exception

    {
        try{
            Long roleId = Long.valueOf(2);
            //Long userId = user.getId();
            log.debug("Create new user");
            User usercheck = userService.findByLogin(user.getLogin());
            if(usercheck != null){
                throw new Exception("le login est déjà utilisé ");

            }
            user.setPassword(passwordEncoder.encode(user.getPassword()));

            List<Role>  roleList = new ArrayList<>()  ;

            //  User userid = userService.findById(roleid).get();
            roleList.add(roleService.findById(roleId)) ;
            user.setRoles(roleList);
            user =  userService.save(user);
            Patient patient= new Patient();
            patient.setUser(user);
            patient.setActive(false);
            //patient.setAffile("");
          //  patient.setDoit("");
            //patient.setNumAffiliation("");
           // patient.setSeanceDays(null);
           // patient.setPriseEnCharges(null);

            patientService.save(patient);
            user.setPassword(null);//hide password for response
        }catch (Exception e ){
            throw  new Exception("incorrect", e);
    }
        return new ResponseEntity(user, HttpStatus.CREATED);
    }


    @PutMapping(path = "/update/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    @CrossOrigin("*")
   // @CrossOrigin("http://localhost:4200/#/vertical/edit-account")
    public ResponseEntity<User> userPatient(@RequestHeader(value = "Authorization") String jwt,
                                                 @RequestBody User userpayload, @PathVariable(value = "id") long userId) {

        //if (jwtTokenUtil.verificationtoken(jwt) ) {

        User user = userService.findById(userId).get();
        user.setBirthDate((userpayload.getBirthDate()));
        user.setFirstName(userpayload.getFirstName());
        user.setLastName(user.getLastName());
        user.setLogin(user.getLogin());
        user.setPassword(user.getPassword());
        return new ResponseEntity(userService.save(user), HttpStatus.OK);
        //  return ResponseEntity.ok(patient);

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
    public Map<String, String> createAuthenticationToken(String token) throws Exception {
        Map<String, String> response = new HashMap<>();
        //response.put("Username", (userDetails.getUsername()));
        //response.put("id", Long.toString( user.get().getId()));
        response.put("Authorization", token);
        //response.put("role",  user.get().getRole());

        return response;

    }

    //@PreAuthorize("hasRole('admin') or hasRole('employer') or hasRole('patient') or hasRole('superadmin')")
    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/profile/{id}")
    public ResponseEntity<?> getUserById(@PathVariable(value = "id") long userId, @RequestHeader(value = "Authorization") String Jwt)
            throws Exception {

        // jwtTokenUtil.verificationtoken(Jwt) ;
        return new ResponseEntity(userService.findById(userId), HttpStatus.OK);

    }










}
