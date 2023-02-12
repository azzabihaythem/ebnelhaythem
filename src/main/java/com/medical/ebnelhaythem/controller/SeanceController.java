package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
@Slf4j
public class SeanceController {

    private SeanceService seanceService;

    private PatientService patientService;


    /**
     * create seance
     * @param seance
     * @return
     */
    @PostMapping(path = "/seances", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSeance(@RequestBody Seance seance)

    {   log.debug("Create new seance");
        return new ResponseEntity(seanceService.save(seance), HttpStatus.CREATED);
    }


}
