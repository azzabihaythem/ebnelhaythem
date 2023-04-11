package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.dto.PatientAndAbscenceDto;
import com.medical.ebnelhaythem.entity.*;
import com.medical.ebnelhaythem.repository.BorderauLastNumberRepository;
import com.medical.ebnelhaythem.repository.FactureLastNumberRepository;
import com.medical.ebnelhaythem.repository.FactureRepository;
import com.medical.ebnelhaythem.utils.JwtUtil;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

import static java.time.temporal.TemporalAdjusters.lastDayOfMonth;

@Component
@AllArgsConstructor
@Slf4j
public class FactureServiceImpl implements FactureService{

    private FactureRepository factureRepository;

    private BorderauLastNumberRepository borderauLastNumberRepository;

    private FacturePdfBuilder facturePdfBuilder;

    private CliniqueService cliniqueService;

    private SeanceService seanceService;

    private PatientService patientService;

    private FactureLastNumberService factureLastNumberService;

    private UserService userService;

    private JwtUtil jwtUtilil;


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
        FactureLastNumber factureLastNumber =  factureLastNumberService.findByCliniqueId(cliniqueId);
        if(factureLastNumber!=null){
            lastFactureNumberByCliniqueId = (Long.parseLong(factureLastNumber.getNumber()) + 1)+"";
            factureLastNumber.setNumber(lastFactureNumberByCliniqueId);
            factureLastNumberService.save(factureLastNumber);
        }else {
             factureLastNumber = new FactureLastNumber(cliniqueService.findByCliniqueId(cliniqueId),"1");
            factureLastNumberService.save(factureLastNumber);
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

    @Override
    public void removeSeanceFromFacture(Seance seance) {
            Facture facture = findBySeancesContains(seance);
            if(facture !=null) {
                facture.getSeances().remove(seance);
                save(facture);
            }
    }

    @Override
    public Facture findBySeancesContains(Seance seance) {
        return factureRepository.findBySeancesContains(seance);
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

    @Override
    public void deleteAllAbscenceSeance(long patientId, List<Integer> absence, LocalDate startDate, LocalDate endDate) {
        SortedSet<Seance> seanceSortedSet = seanceService.findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(patientId + "",
                startDate, endDate);
        if (absence != null && absence.size() > 0) {
            for (Seance aSeance : seanceSortedSet
            ) {
                if (absence.contains(aSeance.getDate().getDayOfMonth())) {
                    removeSeanceFromFacture(aSeance);
                    seanceService.delete(aSeance.getId());
                }
            }
        }
    }

    @Override
    public void postPatientAndSeance(PatientAndAbscenceDto patientAndAbscenceDto, String token,
                                     LocalDate startDate,LocalDate endDate) {
        log.debug("Create/update  patient");

        Patient patient = patientService.findByNumAffiliation(patientAndAbscenceDto.getPatient().getNumAffiliation());

        long cliniqueId = jwtUtilil.getCliniqueId(token).longValue();

        updateLastBorderauNumber(patientAndAbscenceDto, cliniqueId);

        updateLastFactureNumber(patientAndAbscenceDto, cliniqueId);

        updatePatient(patientAndAbscenceDto, patient);




        seanceService.postSeancesOfPatient(patientAndAbscenceDto.getPatient().getId()+""
                , patientAndAbscenceDto.getTypeSeanceId()
                , startDate
                , endDate);

        deleteAllAbscenceSeance(patientAndAbscenceDto.getPatient().getId()
                , patientAndAbscenceDto.getAbscence()
                , startDate
                , endDate);
    }

    private void updatePatient(PatientAndAbscenceDto patientAndAbscenceDto, Patient patient) {
        if(patient !=null){

            patientAndAbscenceDto.getPatient().setId(patient.getId());
            patientAndAbscenceDto.getPatient().setUser(patient.getUser());
            //todo add prise en charge if it is new and dont replace existing one
            patientAndAbscenceDto.getPatient().
                    getPriseEnCharges().get(0).setId(patient.getPriseEnCharges().get(0).getId());
        }


        patientService.save(patientAndAbscenceDto.getPatient());
    }

    private void updateLastFactureNumber(PatientAndAbscenceDto patientAndAbscenceDto, long cliniqueId) {
        if( patientAndAbscenceDto.getFactureNumber()!=null && !"{{factureNumber}}".equals(patientAndAbscenceDto.getFactureNumber())){

            FactureLastNumber factureLastNumber = factureLastNumberService
                    .findByCliniqueId(cliniqueId);
            if(factureLastNumber!=null){
                factureLastNumber.setNumber((Long.parseLong(patientAndAbscenceDto.getFactureNumber())-1)+"");
            }else{
                factureLastNumber = new FactureLastNumber(cliniqueService.findByCliniqueId(cliniqueId),
                        (Long.parseLong(patientAndAbscenceDto.getFactureNumber())-1)+"");
            }
            factureLastNumberService.save(factureLastNumber);
        }
    }

    private void updateLastBorderauNumber(PatientAndAbscenceDto patientAndAbscenceDto, long cliniqueId) {
        //update or create last borderau number
        if( patientAndAbscenceDto.getBorederauNumber()!=null && !"{{borederauNumber}}".equals(patientAndAbscenceDto.getBorederauNumber())){

            BorderauLastNumber borderauLastNumber = borderauLastNumberRepository
                    .findByCliniqueId(cliniqueId);
            if(borderauLastNumber!=null){
                borderauLastNumber.setNumber((Long.parseLong(patientAndAbscenceDto.getBorederauNumber())-1)+"");
            }else{
                borderauLastNumber = new BorderauLastNumber(cliniqueService.findByCliniqueId(cliniqueId),
                        (Long.parseLong(patientAndAbscenceDto.getBorederauNumber())-1)+"");
            }
            borderauLastNumberRepository.save(borderauLastNumber);
        }
    }


}
