package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

public interface SeanceService {

    public List<Seance> getAllSeance() ;
    public Seance save(Seance seance);
    public Optional<Seance> findById(Long id);

    public void  deleteById(Long id);

    public void postSeancesOfPatient(String patientId, String seanceTypeId, LocalDate startDate, LocalDate endDate);
    public SortedSet<Seance> findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(String patientId,
                                                                                        LocalDate startDate,
                                                                                        LocalDate endDate);
    public void saveSeanceIfNotExist(LocalDate localDate,
                                     SeanceType seanceType,
                                     Patient patient);

}
