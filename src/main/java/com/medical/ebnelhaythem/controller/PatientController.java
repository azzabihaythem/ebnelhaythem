package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.dto.PatientAndAbscenceDto;
import com.medical.ebnelhaythem.entity.Clinique;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.service.*;
import com.medical.ebnelhaythem.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.PageRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.data.domain.Pageable;

import java.time.LocalDate;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;


@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
@Slf4j
public class PatientController {

    private UserService userService;

    private PatientService patientService;

    private CliniqueService cliniqueService;

    private SeanceService seanceService;

    private FactureService factureService;

    private JwtUtil jwtUtilil;

    /**
     * Create a new clinique
     * @param clinique
     * @return
     */
    @PostMapping(path = "/clinique", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postClinique(@RequestBody Clinique clinique)

    {log.info("Create new clinique");
        return new ResponseEntity(cliniqueService.save(clinique), HttpStatus.CREATED);
    }

    /**
     * get clinique
     * @param id
     * @return
     */
    @GetMapping(path = "/clinique/{id}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> getClinique(@PathVariable("id") Long id)
    {
        log.info("GET Clinique");
        return new ResponseEntity(cliniqueService.findByCliniqueId(id), HttpStatus.OK);
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


    /**
     * create or update patient and add all seance in a month (need to consider absence)
     * @param patientAndAbscenceDto
     * @return
     */
    @PostMapping(path = "/patients/seances/month/{month}/year/{year}", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postPatientAndSeance(@RequestBody PatientAndAbscenceDto patientAndAbscenceDto,
                                                  @RequestHeader("Authorization") String token ,
                                                  @PathVariable Integer month,
                                                  @PathVariable Integer year){

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.with(lastDayOfMonth());

        factureService.postPatientAndSeance(patientAndAbscenceDto,token,startDate,endDate);

        return new ResponseEntity(HttpStatus.CREATED);
    }


}
