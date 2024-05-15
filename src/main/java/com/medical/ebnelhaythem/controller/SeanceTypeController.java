package com.medical.ebnelhaythem.controller;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;
import com.medical.ebnelhaythem.service.PatientService;
import com.medical.ebnelhaythem.service.SeanceService;
import com.medical.ebnelhaythem.service.SeanceTypeService;
import com.medical.ebnelhaythem.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping(value = "/v1")
@CrossOrigin("*")
public class SeanceTypeController {
   @Autowired
   private SeanceTypeService seanceTypeService ;
    @Autowired
    private PatientService patientService;
    @Autowired
    private SeanceService seanceService ;
    @Autowired
    private UserService userService;
    private static final Logger log = org.slf4j.LoggerFactory.getLogger(PatientController.class);


    @GetMapping("/seancetype/{id}")
    public ResponseEntity<?> getSeancetypeById(@PathVariable(value = "id") long seancetypeId, @RequestHeader(value = "Authorization") String Jwt)
            throws Exception {

        return new ResponseEntity(seanceTypeService.findById(seancetypeId), HttpStatus.OK);

    }


    @PostMapping(path = "/seancetype/add", consumes = MediaType.APPLICATION_JSON_VALUE,
            produces = MediaType.APPLICATION_JSON_VALUE)
    public ResponseEntity<?> postSeancetype(@RequestHeader(value = "Authorization") String Jwt, @RequestBody SeanceType seancetype){
        log.debug("Create new seance");

        seancetype =  seanceTypeService.save(seancetype);
        return new ResponseEntity(seancetype, HttpStatus.CREATED);
    }


    // @PreAuthorize("hasRole('employer') or hasRole('admin') or hasRole('superadmin') or hasRole('Patient') ")
    @PutMapping("/seancetype/update")
    public ResponseEntity<?> updateSeance(@RequestHeader(value = "Authorization") String jwt,
                                          @RequestBody SeanceType seancetypepayload, @RequestParam Long SId) {

        //if (jwtTokenUtil.verificationtoken(jwt) ) {
        SeanceType seanceType = seanceTypeService.findById(SId).get();
        seanceType.setEXONERE(seancetypepayload.getEXONERE());
        seanceType.setMSP(seancetypepayload.getMSP());
        seanceType.setMTHTAXE(seancetypepayload.getMTHTAXE());
        seanceType.setTypeName(seancetypepayload.getTypeName());
        seanceType.setMTTVA(seancetypepayload.getMTTVA());
        return new ResponseEntity(seanceTypeService.save(seanceType), HttpStatus.OK) ;
        //  return ResponseEntity.ok(patient);
    }

    @DeleteMapping(path = "/dseancetype/{id}")
    public ResponseEntity<?> deleteSeancetype(@PathVariable("id") Long id){
        log.debug("delete seance");
        seanceTypeService.deleteById(id);
        return new ResponseEntity( HttpStatus.OK);
    }

    @GetMapping(path = "/seancetype/all")
    public ResponseEntity<SeanceType> getAllSeanceType(@RequestHeader(value = "Authorization") String Jwt)
    {
        return new ResponseEntity(seanceTypeService.getAllSeanceType(), HttpStatus.OK);

    }




}
