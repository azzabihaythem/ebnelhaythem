package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.service.CliniqueService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.UserService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class PatientController {

    private UserService userService;

    private PatientService patientService;

    private CliniqueService cliniqueService;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PatientController.class);

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
     * @param id Patient
     * @return
     */
    @PutMapping(path = "/patients/activate/{id}/{activateValue}")
    public ResponseEntity<?> deactivatePatient(@PathVariable("id") Long id,@PathVariable("active") Boolean active){
        log.debug("set  patient active");
        return new ResponseEntity(patientService.setPatientActive(id,active), HttpStatus.OK);
    }
}
