package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.DAYS_OF_THE_WEEK;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;
import com.medical.ebnelhaythem.repository.PatientRepository;
import com.medical.ebnelhaythem.repository.SeanceRepository;
import com.medical.ebnelhaythem.repository.SeanceTypeRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

@Component
@AllArgsConstructor
@Slf4j
public class SeanceServiceImpl implements SeanceService {
    @Autowired
    private SeanceRepository seanceRepository;
    @Autowired
    private SeanceTypeRepository seanceTypeRepository;
    @Autowired
    private PatientService patientService;

    @Override
    public List<Seance> getAllSeance() {
        return (List<Seance>) seanceRepository.findAll() ;
    }

    @Override
    public Seance save(Seance seance) {
        return seanceRepository.save(seance) ;
    }

    @Override
    public Optional<Seance> findById(Long id) {
        return  seanceRepository.findById(id);
    }

    @Override
    public void deleteById(Long id) {
       seanceRepository.deleteById(id);
    }

    @Override
    public void saveSeanceIfNotExist(LocalDate localDate,
                                     SeanceType seanceType,
                                     Patient patient) {
        try {
            save(new Seance(patient,localDate,seanceType));
        }catch (Exception e){
            log.debug("exception is :"+e.getMessage());
            log.debug("seance already exist");
        }
    }


    /**
     * ADD ALL SEANCE OF ONE PATIENT BETWEEN TWO DATE DEPEND ON HIS days_of_the_weeks SEANCE
     * @param patientId
     * @param seanceTypeId
     * @param startDate
     * @param endDate
     * @return
     */
    @Override
    public void postSeancesOfPatient(String patientId,
                                     String seanceTypeId,
                                     LocalDate startDate,
                                     LocalDate endDate) {

        Optional<Patient> patient = patientService.findById(Long.parseLong(patientId));

        Optional<SeanceType> seanceType = seanceTypeRepository.findById(Long.parseLong(seanceTypeId));

        if(patient.isPresent()
                && seanceType.isPresent()
                && patient.get().getSeanceDays()!= null
                && patient.get().getSeanceDays().size()>0) {
            endDate = endDate.plusDays(1);
            startDate.datesUntil(endDate)
                    .filter(localDate -> patient.get().getSeanceDays()
                            .contains(DAYS_OF_THE_WEEK.valueOf(localDate.getDayOfWeek().name())))
                    .forEach(localDate -> saveSeanceIfNotExist(localDate,seanceType.get(),patient.get()));

        }

    }

    @Override
    public SortedSet<Seance> findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(String patientId,
                                                                                        LocalDate startDate,
                                                                                        LocalDate endDate) {
        return seanceRepository.findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(Long.parseLong(patientId),startDate,endDate);
    }


}


