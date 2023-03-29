package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;
import java.time.LocalDate;
import java.util.List;
import java.util.Optional;
import java.util.SortedSet;

public interface SeanceService {

     public Seance save(Seance seance);

     public void delete(Long seanceId);


     public void saveSeanceIfNotExist(LocalDate localDate,
                                      SeanceType seanceType,
                                      Patient patient);

     public void postSeancesOfPatient(String patientId, String seanceTypeId, LocalDate startDate, LocalDate endDate);

     public  List<Seance> findAll();

     public SortedSet<Seance> findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(String patientId,
                                                                                         LocalDate startDate,
                                                                                         LocalDate endDate);

     public Optional<Seance> findById(Long  seanceId);
}
