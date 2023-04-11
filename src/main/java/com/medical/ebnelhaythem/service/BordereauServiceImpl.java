package com.medical.ebnelhaythem.service;

import com.itextpdf.text.DocumentException;
import com.medical.ebnelhaythem.entity.BorderauLastNumber;
import com.medical.ebnelhaythem.entity.Bordereau;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.repository.BorderauLastNumberRepository;
import com.medical.ebnelhaythem.repository.BordereauRepository;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.text.ParseException;
import java.time.LocalDate;
import java.util.List;

@Component
@AllArgsConstructor
@Slf4j
public class BordereauServiceImpl implements BordereauService{

    private BorderauLastNumberRepository borderauLastNumberRepository;

    private BordereauRepository bordereauRepository;

    private FactureService factureService;

    private CliniqueService cliniqueService;

    private BorderauPdfBuilder bordereauPdfBuilder;

    @Override
    public BorderauLastNumber findByCliniqueId(Long cliniqueId) {
        return borderauLastNumberRepository.findByCliniqueId(cliniqueId);
    }

    @Override
    public BorderauLastNumber save(BorderauLastNumber borderauLastNumber) {
        return borderauLastNumberRepository.save(borderauLastNumber);
    }

    @Override
    public Bordereau createBorderaByPatientsAndDate(List<Long> patientIds, LocalDate startDate,
                                                    LocalDate endDate, long cliniqueId) {

        List<Facture> factureList = factureService.findByDateAndPatientIdIn(endDate,patientIds);

        Bordereau bordereau = bordereauRepository.findByDateAndCliniqueId(endDate,cliniqueId);

        if(bordereau==null){
            bordereau = new Bordereau();
            bordereau.setClinique(cliniqueService.findByCliniqueId(cliniqueId));
            bordereau.setDate(endDate);
            bordereau.setNumber(getLastBorderauNumberByCliniqueId(cliniqueId));
        }
        bordereau.setFactures(factureList);
        return bordereauRepository.save(bordereau);
    }

    @Override
    public Bordereau findByDateAndCliniqueId(LocalDate date, long cliniqueId) {
        return bordereauRepository.findByDateAndCliniqueId(date,cliniqueId);
    }

    @Override
    public ByteArrayInputStream getBorderauPdf(Bordereau bordereau) throws DocumentException, ParseException {
        return bordereauPdfBuilder.doPdf(bordereau);
    }

    private String getLastBorderauNumberByCliniqueId(long cliniqueId) {
        String lastBordereauNumberByCliniqueId = "1";
        BorderauLastNumber bordereauLastNumber =  borderauLastNumberRepository.findByCliniqueId(cliniqueId);
        if(bordereauLastNumber!=null){
            lastBordereauNumberByCliniqueId = (Long.parseLong(bordereauLastNumber.getNumber()) + 1)+"";
            bordereauLastNumber.setNumber(lastBordereauNumberByCliniqueId);
            borderauLastNumberRepository.save(bordereauLastNumber);
        }else {
            bordereauLastNumber = new BorderauLastNumber(cliniqueService.findByCliniqueId(cliniqueId),"1");
            borderauLastNumberRepository.save(bordereauLastNumber);
        }
        return lastBordereauNumberByCliniqueId;
    }
}
