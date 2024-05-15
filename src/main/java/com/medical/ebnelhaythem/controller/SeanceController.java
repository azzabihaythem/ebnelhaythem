package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.*;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import com.medical.ebnelhaythem.service.SeanceTypeService;
import com.medical.ebnelhaythem.service.UserService;
import org.apache.tomcat.util.bcel.Const;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class SeanceController {
   @Autowired
   private SeanceTypeService seanceTypeService ;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SeanceService seanceService ;
    @Autowired
    private UserService userService;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PatientController.class);

    @GetMapping(path = "/listseance")
    public ResponseEntity<Seance> getAllSeance(@RequestHeader(value = "Authorization") String Jwt)

    {
        return new ResponseEntity(seanceService.getAllSeance() , HttpStatus.OK);

    }

    @GetMapping(path = "/listseancetype")
    public ResponseEntity<SeanceType> getAllSeancetype(@RequestHeader(value = "Authorization") String Jwt)
    {
        return new ResponseEntity(seanceTypeService.getAllSeanceType(), HttpStatus.OK);

    }

    /**
     *
     * @param seance
     * @param PatientId
     * @return
     */
    @PostMapping(path = "/seance", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSeance(@RequestHeader(value = "Authorization") String Jwt, @RequestBody Seance seance,
                                        @RequestParam Long PatientId){
        log.debug("Create new seance");
        // Seance patientSeance = seanceService.save(seance.getPatient()) ;
        Patient patientseance = patientService.findById(PatientId).get() ;
        seance.setPatient(patientseance);
        seance.setSeanceType(seanceTypeService.findById(seance.getSeanceType().getId()).get());
       // List<Seance>  seanceListe = new ArrayList<>()  ;
       // seanceListe.add((Seance) seanceService.getAllSeance()) ; // getallseance by patientId
        seance =  seanceService.save(seance);
        return new ResponseEntity(seance, HttpStatus.CREATED);
    }


    @PostMapping(path = "/seance/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSeance(@RequestHeader(value = "Authorization") String Jwt, @RequestBody Seance seance ){
        log.debug("Create new seance");
        // Seance patientSeance = seanceService.save(seance.getPatient()) ;
        Patient patientseance = patientService.findById(seance.getPatient().getId()).get() ;
        seance.setPatient(patientseance);
        seance.setSeanceType(seanceTypeService.findById(seance.getSeanceType().getId()).get());
        // List<Seance>  seanceListe = new ArrayList<>()  ;
        // seanceListe.add((Seance) seanceService.getAllSeance()) ; // getallseance by patientId
        seance =  seanceService.save(seance);
        return new ResponseEntity(seance, HttpStatus.CREATED);
    }

    /**
     *
     * @param id
     * @return
     */
    @DeleteMapping(path = "/dseance/{id}")
    public ResponseEntity<?> deleteSeance(@PathVariable("id") Long id){
        log.debug("delete seance");
        seanceService.deleteById(id);
        return new ResponseEntity( HttpStatus.OK);
    }

   // @PreAuthorize("hasRole('employer') or hasRole('admin') or hasRole('superadmin') or hasRole('Patient') ")
    //@CrossOrigin(origins = "http://localhost:4200")
    @PutMapping("/seance/update")
    public ResponseEntity<?> updateSeance(@RequestHeader(value = "Authorization") String jwt,
                                                 @RequestBody Seance seancepayload, @RequestParam Long SId) {

        //if (jwtTokenUtil.verificationtoken(jwt) ) {
        Seance seance = seanceService.findById(SId).get();
        seance.setPatient(seancepayload.getPatient());
        seance.setSeanceType(seancepayload.getSeanceType());
        seance.setDate(seancepayload.getDate());
        // patient.setDesactivationDate(patientpayload.getDesactivationDate());
        return new ResponseEntity(seanceService.save(seance), HttpStatus.OK) ;
        //  return ResponseEntity.ok(patient);
    }

    @GetMapping("/seance/{id}")
    public ResponseEntity<?> getSeanceById(@PathVariable(value = "id") long seanceId, @RequestHeader(value = "Authorization") String Jwt)
            throws Exception {


        return new ResponseEntity(seanceService.findById(seanceId), HttpStatus.OK);

    }

    /**
     *
     * @param patientIds
     * @param startDate
     * @param endDate
     * @return
     */

    @PostMapping(path = "/list/seances/typeSeanceId/{typeSeanceId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<?> postSeancesOfListPatient(@RequestBody String patientIds,
                                                      @PathVariable String typeSeanceId,
                                                      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate
    )

    {
        log.debug("Create list seance");
        //log.debug("patientId is "+patientIds.size());
        log.debug("startDate is "+startDate);
        log.debug("endDate is "+endDate);
         seanceService.postSeancesOfPatient(patientIds,typeSeanceId,startDate,endDate);
       /** patientIds.forEach(patientId -> seanceService.postSeancesOfPatient(patientId,typeSeanceId,startDate,endDate)); **/

        return new ResponseEntity(HttpStatus.CREATED);
    }

/*
    @PostMapping(path = "/list/seancesAuto/add")
    public ResponseEntity<?> postSeancesOfListPatients(@RequestBody Seance seance

    )

    {

        Patient patientseance = patientService.findById(seance.getPatient().getId()).get() ;
        seance.setPatient(patientseance);

       Long NbSeanceDays = seance.getPatient().getSeanceDays().stream().count() ;

        seance.setSeanceType(seanceTypeService.findById(seance.getSeanceType().getId()).get());
        seance.getPatient().getPriseEnCharges().startDate.datesUntil(endDate)
                        DAYS_OF_THE_WEEK.valueOf(getDayOfWeek().name()))
                .forEach( save());

        seance =  seanceService.save(seance);



        return new ResponseEntity(seance, HttpStatus.CREATED);
    } */




}
