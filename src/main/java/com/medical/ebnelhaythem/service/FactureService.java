package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.dto.PatientAndAbscenceDto;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.entity.Seance;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

public interface FactureService {

    public ByteArrayInputStream getFacturePatientPdf(List<Facture> factureList) throws DocumentException;

    public Facture  createPatientFacture(String patientId, LocalDate startDate,
                                         LocalDate endDate,long cliniqueId);

    public List<Facture>  createListPatientFacture(List<String> patientIds,
                                                   LocalDate startDate,
                                                   LocalDate endDate,
                                                   long cliniqueId);

    public Facture save(Facture facture);

    List<Facture> findByDateAndPatientIdIn(LocalDate endDate,List<Long> patientIds);

    void removeSeanceFromFacture(Seance seance);

    Facture findBySeancesContains(Seance seance);

    void deleteAllAbscenceSeance(long patientId, List<Integer> abscence, LocalDate startDate, LocalDate endDate);

    void postPatientAndSeance(PatientAndAbscenceDto patientAndAbscenceDto, String token,
                              LocalDate startDate,LocalDate endDate);
}
