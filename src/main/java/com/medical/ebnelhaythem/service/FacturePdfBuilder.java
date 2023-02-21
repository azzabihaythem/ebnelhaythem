package com.medical.ebnelhaythem.service;


import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.entity.Seance;

import java.io.ByteArrayInputStream;
import java.util.List;

public interface FacturePdfBuilder {

    public ByteArrayInputStream doPdf(Facture facture) throws DocumentException;

}
