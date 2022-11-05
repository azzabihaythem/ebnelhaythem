package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.*;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import com.medical.ebnelhaythem.service.SeanceTypeService;
import com.medical.ebnelhaythem.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

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

}
