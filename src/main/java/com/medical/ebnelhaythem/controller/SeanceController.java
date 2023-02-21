package com.medical.ebnelhaythem.controller;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.service.FactureService;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import com.medical.ebnelhaythem.utils.JwtUtil;
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

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;


@RestController
@RequestMapping(value = "/v1")
@AllArgsConstructor
@Slf4j
public class SeanceController {

    private SeanceService seanceService;

    private PatientService patientService;

    private FactureService factureService;

    private JwtUtil jwtUtilil;


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
     * @param response
     * @param patientIds
     * @param month
     * @param year
     * @return
     * @throws DocumentException
     */
    @PostMapping(path = "/list/factures/month/{month}/year/{year}")
    public ResponseEntity<InputStreamResource>  getFacturePatient(HttpServletResponse response,
                                                                  @RequestBody List<String> patientIds,
                                                                  @RequestHeader("Authorization") String token ,
                                                                  @PathVariable Integer month,
                                                                  @PathVariable Integer year) throws DocumentException {


        response.setContentType("application/pdf");

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.with(lastDayOfMonth());

        Facture facture = factureService.createPatientFacture(patientIds.get(0),startDate,endDate,jwtUtilil.getCliniqueId(token));

        ByteArrayInputStream bis = factureService.getFacturePatient(facture);

        var headers = new HttpHeaders();

        headers.add("Content-Disposition", "inline; filename=FacturesParPatient"
                +month+"/"+year+".pdf");

        return ResponseEntity
                .ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(bis));

    }


}
