package com.medical.ebnelhaythem.controller;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.core.io.InputStreamResource;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayInputStream;
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

    /**
     *
     * @param patientIds
     * @param startDate
     * @param endDate
     * @return
     */
    @PostMapping(path = "/list/factures/startDate/{startDate}/endDate/{endDate}")
    public ResponseEntity<InputStreamResource>  getFacturePatient(HttpServletResponse response,
                                                                  @RequestBody List<String> patientIds,
                                                                  @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate startDate,
                                                                  @PathVariable @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate endDate
    ) throws DocumentException {
        log.debug("getFacturePatient");
        log.debug("patientId is "+patientIds.size());
        log.debug("startDate is "+startDate);
        log.debug("endDate is "+endDate);

        response.setContentType("application/pdf");

        ByteArrayInputStream bis = seanceService.getFacturePatient(patientIds,startDate,endDate);
        log.debug("endDate is "+endDate);
        var headers = new HttpHeaders();
        headers.add("Content-Disposition", "inline; filename=citiesreport.pdf");
        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }


}
