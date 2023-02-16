package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;


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

    {
        log.debug("Create new seance");
        return new ResponseEntity(seanceService.save(seance), HttpStatus.CREATED);
    }


    /**
     *
     * @param patientIds
     * @param startDate
     * @param endDate
     * @return
     */
    @PostMapping(path = "/list/seances/typeSeanceId/{typeSeanceId}/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<?> postSeancesOfListPatient(@RequestBody List<String> patientIds,
                                                      @PathVariable String typeSeanceId,
                                                      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                      @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate
    )

    {
        log.debug("Create list seance");
        log.debug("patientId is "+patientIds.size());
        log.debug("startDate is "+startDate);
        log.debug("endDate is "+endDate);

        patientIds.forEach(patientId -> seanceService.postSeancesOfPatient(patientId,typeSeanceId,startDate,endDate));

        return new ResponseEntity(HttpStatus.CREATED);
    }


}
