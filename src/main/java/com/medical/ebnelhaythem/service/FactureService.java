package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Facture;
import java.io.ByteArrayInputStream;
import java.time.LocalDate;

public interface FactureService {

    public ByteArrayInputStream getFacturePatient(Facture facture) throws DocumentException;

    public Facture  createPatientFacture(String patientId, LocalDate startDate,
                                         LocalDate endDate,long cliniqueId) throws DocumentException;

    public Facture save(Facture facture);

}
