package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.service.CliniqueService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.UserService;
import com.medical.ebnelhaythem.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
@Slf4j
public class PatientController {

    private UserService userService;

    private PatientService patientService;

    private CliniqueService cliniqueService;

    private JwtUtil jwtUtilil;

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
     * get payment by page
     * @param page
     * @param numberOfElements
     * @return list of patient by page
     */
    @GetMapping(path = "/patients/{page}/{numberOfElements}")
    public ResponseEntity<?> getPatientPage(@PathVariable("page") int page, @PathVariable("numberOfElements") int numberOfElements){
        log.debug("get patient by page");
        Pageable pageablePageAndNumberOfElements = PageRequest.of(page, numberOfElements);
        return new ResponseEntity(patientService.findAll(pageablePageAndNumberOfElements), HttpStatus.OK);
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
     * @param patientId Patient
     * @return
     */
    @PutMapping(path = "/patients/{patientId}/active/{active}")
    public ResponseEntity<?> deactivatePatient(@PathVariable("patientId") Long patientId,@PathVariable("active") Boolean active){
        log.debug("set  patient active");
        patientService.setPatientActive(patientId,active);
        return new ResponseEntity(HttpStatus.OK);
    }

    /**
     * updateAllPatientStatus endPoint
     * @param active
     * @return update all patients status
     */
    @PutMapping(path = "/patients/all/active/{active}")
    public ResponseEntity<?> updateAllPatientStatus(@PathVariable("active") Boolean active,
                                                    @RequestHeader("Authorization") String token){
        log.debug("updateAllPatientStatus");
        Long cliniqueId = jwtUtilil.getCliniqueId(token).longValue();
        patientService.updateAllPatientStatus(active,cliniqueId);
        return new ResponseEntity( HttpStatus.OK);
    }
}
