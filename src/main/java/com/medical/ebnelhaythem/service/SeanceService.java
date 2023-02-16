package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;

import java.time.LocalDate;

public interface SeanceService {

     public Seance save(Seance seance);

     public void saveSeanceIfNotExist(LocalDate localDate,
                                      SeanceType seanceType,
                                      Patient patient);

     public void postSeancesOfPatient(String patientId, String seanceTypeId, LocalDate startDate, LocalDate endDate);

}
