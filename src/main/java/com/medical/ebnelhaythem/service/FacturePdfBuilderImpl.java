package com.medical.ebnelhaythem.service;

import com.itextpdf.text.*;
import com.itextpdf.text.pdf.BaseFont;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import com.medical.ebnelhaythem.entity.*;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Component;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.util.Calendar;
import java.util.List;

@Component
@AllArgsConstructor
public class FacturePdfBuilderImpl implements  FacturePdfBuilder{

    public static Font normal = FontFactory.getFont(BaseFont.IDENTITY_H, 10,
            Font.NORMAL);
    public static Font normal2 = FontFactory.getFont(BaseFont.IDENTITY_H, 12,
            Font.NORMAL);

    @Override
    public ByteArrayInputStream doPdf(Facture facture) throws DocumentException {

        if (facture.getSeances().size() > 0) {
            Paragraph preface;
            //todo complete this information and get them out of this class
            Clinique clinique = facture.getSeances().get(0).getPatient().getUser().getClinique();
            Patient patient = facture.getSeances().get(0).getPatient();
            User user = facture.getSeances().get(0).getPatient().getUser();
            // Facture facture =  new Facture();
            //facture.setId(1);//todo delete this line, value will be generated
            //facture.setNumber("00001");
            //facture.setSeances(seanceList);



            Document doc = new Document();
            ByteArrayOutputStream out = new ByteArrayOutputStream();
            PdfWriter.getInstance(doc, out);
            doc.open();

            preface = new Paragraph(new Phrase("", normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);


            preface = new Paragraph(new Phrase("" + clinique.getNom(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            preface = new Paragraph(new Phrase("" + clinique.getAdress(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            preface = new Paragraph(new Phrase("T.V.A : " + clinique.getTva(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);


            preface = new Paragraph(new Phrase("N°EMPLOYEUR : " + clinique.getEmployNumber(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            preface = new Paragraph(new Phrase("TEL: " + clinique.getTel(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            preface = new Paragraph(new Phrase("REG DE COMMERCE : " + clinique.getRegistreDeCmmerce(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            preface = new Paragraph(new Phrase("\n", normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            preface = new Paragraph(new Phrase("\n", normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);


            if (facture.getNumber() == null) {
                preface = new Paragraph(new Phrase("FACTURE D'HEMODIALYSE N° " + facture.getId(), normal));
                preface.setAlignment(Element.ALIGN_CENTER);
                doc.add(preface);
            } else {
                preface = new Paragraph(new Phrase("FACTURE D'HEMODIALYSE N° " + facture.getNumber(), normal));
                preface.setAlignment(Element.ALIGN_CENTER);
                doc.add(preface);

            }

            preface = new Paragraph(new Phrase("", normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            preface = new Paragraph(new Phrase("", normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            preface = new Paragraph(new Phrase("\n", normal));
            preface.setAlignment(Element.ALIGN_LEFT);


            doc.addCreator("Zannovich");


            preface = new Paragraph(new Phrase("     Doit                         : " +patient.getDoit(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            preface = new Paragraph(new Phrase("     Affil°                        : " + patient.getAffile(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            try {
                preface = new Paragraph(new Phrase("     Nom du patient       : "
                        + user.getFirstName() + " " + user.getLastName(), normal));

            } catch (Exception e) {

                preface = new Paragraph(new Phrase("     Nom du patient       : "
                        , normal));
            }
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);


            final String OLD_FORMAT = "yyyy-MM-dd";
            final String NEW_FORMAT = "dd/MM/yyyy";


            String newDateString;

           // SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
           // sdf.applyPattern(NEW_FORMAT);


            preface = new Paragraph(new Phrase("     N°Affilation             : " + patient.getAffile(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            preface = new Paragraph(new Phrase("     Prise en charge      : N°" + patient.getPriseEnCharges() + " DU " + patient.getPriseEnCharges().get(0).getStartDate() + " AU " + patient.getPriseEnCharges().get(0).getEndDate(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            preface = new Paragraph(new Phrase("     Nombre de Séance      : " + facture.getSeances().size(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            Calendar cal = Calendar.getInstance();
            cal.set(Calendar.MONTH, facture.getSeances().get(0).getDate().getMonth().getValue());
            cal.set(Calendar.YEAR, facture.getSeances().get(0).getDate().getYear() + 1900);

            cal.set(Calendar.DAY_OF_MONTH, 1);// This is necessary to get proper results
            cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
            cal.getTime();
            preface = new Paragraph(new Phrase("     Date Facture          : " + cal.getTime(), normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            preface = new Paragraph(new Phrase("\n", normal));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);

            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(100.0f);
            table.setWidths(new float[]{4.0f, 2.0f, 2.0f, 2.0f, 2.0f, 2.0f});
            table.setSpacingBefore(10);

            // define font for table header row
            Font font = FontFactory.getFont(FontFactory.HELVETICA);

            font.setColor(BaseColor.WHITE);
            // define table header cell
            PdfPCell cell = new PdfPCell();
            cell.setBackgroundColor(BaseColor.WHITE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setPadding(5);
            // write table header
            cell.setPhrase(new Phrase("Jour de pr�sence", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell.setPhrase(new Phrase("MT,H,TAXE", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            cell.setPhrase(new Phrase("MT T,V,A", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            cell.setPhrase(new Phrase("EXONERE", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            cell.setPhrase(new Phrase("M,S,P", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);
            cell.setPhrase(new Phrase("T,T,C", normal));
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            table.addCell(cell);

            int i = 1;

            Long MTHTAXE = 0L;
            Long MTTVA = 0L;
            Long EXONERE = 0L;
            Long MSP = 0L;

            Long totalMTHTAXE = 0L;
            Long totalMTTVA = 0L;
            Long totalEXONERE = 0L;
            Long totalMSP = 0L;
            Long totalGlobal = 0L;
            if (facture.getSeances().size() > 0) {
                for (Seance aSeance : facture.getSeances()) {

                    if (i == 1) {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPhrase(new Phrase("1 ére seance  "
                                + aSeance.getDate(), normal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cell);
                        i++;
                    } else {
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        cell.setPhrase(new Phrase(i + " éme seance "
                                + aSeance.getDate(), normal));
                        cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                        cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                        table.addCell(cell);

                        i++;
                    }

                    MTHTAXE = Long.parseLong(aSeance.getSeanceType().getMTHTAXE());
                    MTTVA = Long.parseLong(aSeance.getSeanceType().getMTTVA());
                    EXONERE = Long.parseLong(aSeance.getSeanceType().getEXONERE());
                    MSP = Long.parseLong(aSeance.getSeanceType().getMSP());
                    totalMTHTAXE = totalMTHTAXE + MTHTAXE;
                    totalMTTVA = totalMTTVA + MTTVA;
                    totalEXONERE = totalEXONERE + EXONERE;
                    totalMSP = totalMSP + MSP;

                    String str = MTHTAXE + "";
                    str = new StringBuilder(str).insert(str.length() - 3, ",")
                            .toString();
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPhrase(new Phrase(str, normal));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    str = MTTVA + "";
                    str = new StringBuilder(str).insert(str.length() - 3, ",")
                            .toString();
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPhrase(new Phrase(str, normal));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    str = EXONERE + "";
                    str = new StringBuilder(str).insert(str.length() - 3, ",")
                            .toString();
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPhrase(new Phrase(str, normal));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    str = MSP + "";
                    str = new StringBuilder(str).insert(str.length() - 3, ",")
                            .toString();
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPhrase(new Phrase(str, normal));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    str = (MTHTAXE + MTTVA + EXONERE + MSP) + "";
                    str = new StringBuilder(str).insert(str.length() - 3, ",")
                            .toString();
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    cell.setPhrase(new Phrase(str, normal));
                    cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                    cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                    table.addCell(cell);
                    totalGlobal = totalGlobal + MTHTAXE + MTTVA + EXONERE + MSP;

                }
                cell.setPhrase(new Phrase("TOTAL", normal2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                String str = totalMTHTAXE + "";
                str = new StringBuilder(str).insert(str.length() - 3, ",")
                        .toString();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPhrase(new Phrase(str, normal2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                str = totalMTTVA + "";
                str = new StringBuilder(str).insert(str.length() - 3, ",")
                        .toString();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPhrase(new Phrase(str, normal2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                str = totalEXONERE + "";
                str = new StringBuilder(str).insert(str.length() - 3, ",")
                        .toString();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPhrase(new Phrase(str, normal2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                str = totalMSP + "";
                str = new StringBuilder(str).insert(str.length() - 3, ",")
                        .toString();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPhrase(new Phrase(str, normal2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
                str = totalGlobal + "";
                str = new StringBuilder(str).insert(str.length() - 3, ",")
                        .toString();
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                cell.setPhrase(new Phrase(str, normal2));
                cell.setHorizontalAlignment(Element.ALIGN_CENTER);
                cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
                table.addCell(cell);
            }

            doc.add(table);

            String dinars = FrenchNumberToWords.convert(totalGlobal / 1000) + " dinars";
            String Millimes = FrenchNumberToWords.convert(totalGlobal % 1000) + " Millimes";

            preface = new Paragraph(new Phrase("\n", normal2));
            preface.setAlignment(Element.ALIGN_LEFT);
            doc.add(preface);
            if (!dinars.equals("zéro dinars") && !Millimes.equals("zéro Millimes")) {
                preface = new Paragraph(new Phrase("Arretée la présente facture à la somme de : " + dinars + " et " + Millimes, normal2));
                preface.setAlignment(Element.ALIGN_LEFT);
                doc.add(preface);
            } else if (!dinars.equals("zéro dinars") && Millimes.equals("zéro Millimes")) {
                preface = new Paragraph(new Phrase("Arretée la présente facture à la somme de : " + dinars, normal2));
                preface.setAlignment(Element.ALIGN_LEFT);
                doc.add(preface);

            } else if (dinars.equals("zéro dinars") && !Millimes.equals("zéro Millimes")) {
                preface = new Paragraph(new Phrase("Arretée la présente facture à la somme de : " + Millimes, normal2));
                preface.setAlignment(Element.ALIGN_LEFT);
                doc.add(preface);

            }

        preface = new Paragraph(new Phrase("\n", normal2));
        preface.setAlignment(Element.ALIGN_LEFT);
        doc.add(preface);
        preface = new Paragraph(new Phrase("BANQUE : " + clinique.getBanqueName(), normal2));
        preface.setAlignment(Element.ALIGN_LEFT);
        doc.add(preface);
        preface = new Paragraph(new Phrase("N°COMPTE : " + clinique.getBanqueNumber(), normal2));
        preface.setAlignment(Element.ALIGN_LEFT);
        doc.add(preface);
        preface = new Paragraph(new Phrase("                                                                           SIGNATURE", normal2));
        preface.setAlignment(Element.ALIGN_LEFT);
        doc.add(preface);

        doc.close();

            return new ByteArrayInputStream(out.toByteArray());
        }
        return null;
    }
}
