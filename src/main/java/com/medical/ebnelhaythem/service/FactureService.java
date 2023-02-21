package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Facture;

import java.io.ByteArrayInputStream;
import java.time.LocalDate;
import java.util.List;

public interface FactureService {

    public ByteArrayInputStream getFacturePatient(List<String> patientIds, LocalDate startDate, LocalDate endDate) throws DocumentException;

    public Facture save(Facture facture);

}
