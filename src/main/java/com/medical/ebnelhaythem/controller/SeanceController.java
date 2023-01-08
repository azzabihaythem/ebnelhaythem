package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
public class SeanceController {

    private SeanceService seanceService;

    private PatientService patientService;

    private static final Logger log = org.slf4j.LoggerFactory.getLogger(SeanceController.class);

    /**
     * create seance
     * @param seance
     * @return
     */
    @PostMapping(path = "/seance", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSeance(@RequestBody Seance seance)

    {   log.debug("Create new clinique");
        return new ResponseEntity(seanceService.save(seance), HttpStatus.CREATED);
    }


}
