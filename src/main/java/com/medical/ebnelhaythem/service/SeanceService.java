package com.medical.ebnelhaythem.service;

import com.itextpdf.text.Document;
import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.SeanceType;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

public interface SeanceService {

     public Seance save(Seance seance);

     public void saveSeanceIfNotExist(LocalDate localDate,
                                      SeanceType seanceType,
                                      Patient patient);

     public void postSeancesOfPatient(String patientId, String seanceTypeId, LocalDate startDate, LocalDate endDate);

     public ByteArrayInputStream getFacturePatient(List<String> patientIds, LocalDate startDate, LocalDate endDate) throws DocumentException;

}
