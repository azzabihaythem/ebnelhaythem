package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.entity.FactureLastNumber;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.repository.FactureLastNumberRepository;
import com.medical.ebnelhaythem.repository.FactureRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.SortedSet;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Component
@AllArgsConstructor
@Slf4j
public class FactureServiceImpl implements FactureService{

    private FacturePdfBuilder facturePdfBuilder;

    private CliniqueService cliniqueService;

    private SeanceService seanceService;

    private PatientService patientService;

    private FactureRepository factureRepository;

    private FactureLastNumberRepository factureLastNumberRepository;


    @Override
    public ByteArrayInputStream getFacturePatientPdf(List<Facture> factureList) throws DocumentException {

        return facturePdfBuilder.doPdf(factureList);
    }

    @Override
    public Facture  createPatientFacture(String patientId,
                                         LocalDate startDate,
                                         LocalDate endDate,
                                         long cliniqueId) {

        Facture  facture = factureRepository.findByPatientIdAndDate(Long.parseLong(patientId),endDate);
        SortedSet<Seance> seanceList = seanceService.
                findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(patientId,startDate,endDate);
        if(facture==null){
            facture = new Facture();
            //todo add test if patient doesn't exist
            facture.setPatient(patientService.findById(Long.parseLong(patientId)).get());
            facture.setDate(endDate);
            facture.setNumber(getLastFactureNumberByCliniqueId(cliniqueId));
        }
        facture.setSeances(seanceList);

        return factureRepository.save(facture);
    }

    @Override
    public List<Facture>  createListPatientFacture(List<String> patientIds,
                                         LocalDate startDate,
                                         LocalDate endDate,
                                         long cliniqueId) {
        List<Facture> factureList = new ArrayList<>();

        for (String patientId:patientIds) {
            factureList.add(createPatientFacture(patientId,startDate,endDate,cliniqueId));
        }
        return  factureList;
    }

    private String getLastFactureNumberByCliniqueId(long cliniqueId) {
        String lastFactureNumberByCliniqueId = "1";
        FactureLastNumber factureLastNumber =  factureLastNumberRepository.findByCliniqueId(cliniqueId);
        if(factureLastNumber!=null){
            lastFactureNumberByCliniqueId = (Long.parseLong(factureLastNumber.getNumber()) + 1)+"";
            factureLastNumber.setNumber(lastFactureNumberByCliniqueId);
            factureLastNumberRepository.save(factureLastNumber);
        }else {
             factureLastNumber = new FactureLastNumber(cliniqueService.findByCliniqueId(cliniqueId),"1");
            factureLastNumberRepository.save(factureLastNumber);
        }
        return lastFactureNumberByCliniqueId;
    }


    @Override
    public Facture save(Facture facture) {
        return null;
    }

    @Override
    public List<Facture> findByDateAndPatientIdIn(LocalDate endDate,List<Long> patientIds) {
        return factureRepository.findByDateAndPatientIdIn(endDate,patientIds);
    }

    public Facture getFactureByPatientId(String patientId, Integer month,Integer year){

        Facture facture = new Facture();

        LocalDate startDate = LocalDate.of(year, month, 1);

        LocalDate endDate = startDate.with(lastDayOfMonth());

        SortedSet<Seance> patientSeanceList = seanceService.findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(
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
           Facture lastOne = factureRepository.findTop1ByPatient_User_CliniqueIdOrderByIdDesc(patientSeanceList.stream()
                   .findFirst().get().getPatient()
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
