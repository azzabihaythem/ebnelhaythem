package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Bordereau;

import java.io.ByteArrayInputStream;
import java.text.ParseException;

public interface BorderauPdfBuilder {

    public ByteArrayInputStream doPdf(Bordereau bordereau)
            throws DocumentException, ParseException;
}
