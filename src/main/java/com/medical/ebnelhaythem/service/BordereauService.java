package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.BorderauLastNumber;
import com.medical.ebnelhaythem.entity.Bordereau;
import com.medical.ebnelhaythem.entity.Facture;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

public interface BordereauService {

    BorderauLastNumber findByCliniqueId(Long cliniqueId);

    Bordereau createBorderaByPatientsAndDate(List<Long> patientIds, LocalDate startDate, LocalDate endDate, long cliniqueId);

    ByteArrayInputStream getBorderauPdf(Bordereau bordereau) throws DocumentException, ParseException;
}
