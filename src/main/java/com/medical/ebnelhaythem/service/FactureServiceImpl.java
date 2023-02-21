package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.repository.FactureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Component
@AllArgsConstructor
@Slf4j
public class FactureServiceImpl implements FactureService{

    private FacturePdfBuilder facturePdfBuilder;

    private SeanceService seanceService;

    private FactureRepository factureRepository;


    @Override
    public ByteArrayInputStream getFacturePatient(List<String> patientIds, LocalDate startDate, LocalDate endDate)
            throws DocumentException {

        //todo remplace by the true value of listseance ; findAll is only for test

        List<Facture> factureList = new ArrayList<>();

        return facturePdfBuilder.doPdf(seanceService.findAll());
    }

    @Override
    public Facture save(Facture facture) {
        return null;
    }

    public Facture getFactureByPatientId(String patientId, Integer month,Integer year){

        Facture facture = new Facture();

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.with(lastDayOfMonth());

        List<Seance> patientSeanceList = seanceService.findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(
                patientId,
                startDate,
                endDate);

        List<Facture> patientFactureList = factureRepository.findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(
                patientId,
                startDate,
                endDate);

        String factureNumber = "1";

        if( patientFactureList.size() == 1){
            facture = patientFactureList.get(0);
        }else if(patientSeanceList.size()>0){
           Facture lastOne = factureRepository.findTop1ByPatient_User_CliniqueIdOrderByIdDesc(patientSeanceList.get(0).getPatient()
                    .getUser().getClinique().getId());
           if(lastOne != null){
               factureNumber = (Long.parseLong(lastOne.getNumber())+1)+"";
               }
        }


        facture.setSeances(patientSeanceList);

        facture.setDate(endDate);

        facture.setNumber(factureNumber);
        
        return save(facture);

    }



}
