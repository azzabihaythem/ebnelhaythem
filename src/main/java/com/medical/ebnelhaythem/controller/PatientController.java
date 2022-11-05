package com.medical.ebnelhaythem.controller;

import antlr.Token;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Role;
import com.medical.ebnelhaythem.entity.User;
import com.medical.ebnelhaythem.filter.AuthenticationFilter;
import com.medical.ebnelhaythem.service.JwtService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.RoleService;
import com.medical.ebnelhaythem.service.UserService;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.rest.webmvc.ResourceNotFoundException;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.util.*;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class PatientController {
    @Autowired
    private PatientService patientService;
    @Autowired
    private UserService userService;
    @Autowired
    private JwtService jwtTokenUtil;
    @Autowired
    private RoleService roleservice ;


    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PatientController.class);


   //@PreAuthorize("hasRole('patient') or hasRole('employer') ")
    @GetMapping(path = "/listPatient")
    public ResponseEntity<Patient> getAllPatient(@RequestHeader(value = "Authorization") String Jwt)

    {
        return new ResponseEntity(patientService.getAllPatient(), HttpStatus.OK);

    }


    /**
     *  show list patient by role
     * @param Jwt
     * @return
     * @throws Exception
     */

    @PreAuthorize("hasRole('admin') or hasRole('employer') or hasRole('patient')")
    @GetMapping("/patient/show")
    public List<Patient> getAllClients(@RequestHeader(value = "Authorization") String Jwt){
        List<Patient> listPatient = null;
        java.util.Optional<Patient> patient = null;

        //jwtTokenUtil.verificationtoken(Jwt) ;
          //  if (role == "admin" || role == "employer" || role == "patient")
                listPatient = (List<Patient>) patientService.getAllPatient();
            return listPatient;
    }


    /**
     * get patient by id
     * @param patientId
     * @param Jwt
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('admin') or hasRole('employer') or hasRole('patient') or hasRole('superadmin')")
    //@CrossOrigin(origins = "http://localhost:4200")
    @GetMapping("/patient/{id}")
    public ResponseEntity<?> getPatientById(@PathVariable(value = "id") long patientId, @RequestHeader(value = "Authorization") String Jwt)
            throws Exception {

               // jwtTokenUtil.verificationtoken(Jwt) ;
                return new ResponseEntity(patientService.findById(patientId), HttpStatus.OK);

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
        User patientUser = userService.save(patient.getUser()) ;
        List<Role>  roleList = new ArrayList<>()  ;
        roleList.add(roleservice.findById(4L)) ;
        patientUser.setRoles(roleList);
        patient.setUser(patientUser);
        return new ResponseEntity(patientService.save(patient), HttpStatus.CREATED);
    }

 /*
    @ModelAttribute
    public User getUser(@PathVariable Long id) {
        return getUser(id);
    } */

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
     * update patient (with user and prise en charge) endPoint
     * @param patient
     * @return
     */
    @PutMapping(path = "/updatep", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> updatePatient(@RequestBody Patient patient,@RequestHeader(value = "Authorization") String Jwt){
        log.debug("update patient");
        return new ResponseEntity(patientService.save(patient), HttpStatus.OK);
    }



    @PreAuthorize("hasRole('employer') or hasRole('admin') or hasRole('superadmin') or hasRole('Patient') ")
    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/patient/update")
    public ResponseEntity<Patient> updatePatient(@RequestHeader(value = "Authorization") String jwt,
                                                 @RequestBody Patient patientpayload, @RequestParam Long PId) {

            //if (jwtTokenUtil.verificationtoken(jwt) ) {

                Patient patient = patientService.findById(PId).get() ;
                patient.setUser(patientpayload.getUser());
                patient.setDoit(patientpayload.getDoit());
                patient.setAffile(patientpayload.getAffile());
                patient.setNumAffiliation(patientpayload.getNumAffiliation());
                patient.setActive(patientpayload.getUser().isActive());
                patient.setSeanceDays(patientpayload.getSeanceDays());
               // patient.setDesactivationDate(patientpayload.getDesactivationDate());
                return new ResponseEntity(patientService.save(patient), HttpStatus.OK) ;
              //  return ResponseEntity.ok(patient);



    }

    /**
     * Delete patients 1
     * @param id
     * @return
     */
    @DeleteMapping(path = "/dpatients/{id}")
    public ResponseEntity<?> deletePatient(@PathVariable("id") Long id){
        log.debug("delete patient");
        patientService.deleteById(id);
        return new ResponseEntity( HttpStatus.OK);
    }

    /**
     *  Delete patients 2
     * @param Jwt
     * @param PatientId
     * @param id
     * @return
     * @throws Exception
     */
    @PreAuthorize("hasRole('admin') or hasRole('employer')")
    @DeleteMapping("/patients/delete/{id}")
    public Map<String, Boolean> deleteClient(@RequestHeader(value = "Authorization") String Jwt,
                                             @PathVariable(value = "id") Long PatientId,
                                             @RequestParam(value ="idUser") Long id)
            throws Exception {
        Map<String, Boolean> response = new HashMap<>();

        try {

            if(jwtTokenUtil.verificationtoken(Jwt))
            {
                Patient patient = patientService.findById(PatientId)
                        .orElseThrow(() -> new ResourceNotFoundException("Client not found for this id :: " + PatientId));
                java.util.Optional<User> isexistUser = null;
                isexistUser = userService.findById(id);
                if (isexistUser.isPresent()) {
                    User user = isexistUser.get();
                   // Patient.setUser(user);
                    // patientService.delete(patient);
                    //ClientRepo.delete(Client);
                    response.put("deleted", Boolean.TRUE);
                }
            }
        }catch (Exception e) {
            throw new Exception("Incorrect ", e);
        }
        return response;
    }



    /**
     *  desactivate patients
     * @param id
     * @return
     */
    @PutMapping(path = "/desactivate/patients/{id}")
    public ResponseEntity<?> desactivatePatient(@PathVariable("id") Long id){
        log.debug("desactivate patient");
        return new ResponseEntity(patientService.desactivatePatient(id), HttpStatus.OK);
    }


}
