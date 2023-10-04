package com.medical.ebnelhaythem.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Calendar;
import com.medical.ebnelhaythem.entity.Bordereau;
import com.medical.ebnelhaythem.entity.Facture;
import com.medical.ebnelhaythem.entity.SeanceType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;


@Service
public class FctrsServiceImpl implements FctrsService {



	@Autowired
	BordereauService bordereauService;

	@Autowired
	CliniqueService cliniqueService;

	@Autowired
	FactureService factureService;

	@Autowired
	SeanceService seanceService;



	@Override
	public String createTextFile(Bordereau bordereau) {
		String borderauLines =  borderauLine(bordereau);
		borderauLines = borderauLines +factureLines(bordereau);
		return borderauLines;
	}


	@Override
	public String borderauLine(Bordereau bordereau) {
		// TODO Auto-generated method stub
		String borderauLine = "";
		borderauLine = "1"; // type borderau
		borderauLine = borderauLine + bordereau.getDate().getYear();// year borderau

		String borderauNumber = bordereau.getNumber();

		System.out.println("****************borderauNumber "+borderauNumber);
		while (borderauNumber.length() < 3) {

			borderauNumber = "0" + borderauNumber;
		}
		borderauLine = borderauLine + borderauNumber;

		// added 2017 02
		borderauLine = borderauLine + "02";

		borderauLine = borderauLine
				+ bordereau.getClinique().getCodeCentre();// code
																	// centre

		borderauLine = borderauLine + bordereau.getDate().getYear();
		borderauLine = borderauLine + "00000000";
		borderauLine = borderauLine
				+ bordereau.getClinique().getCodeBureauxRegional();// code
																			// bureau
																			// r�gional
		borderauLine = borderauLine + "21";

		// dded 2017
		borderauLine = borderauLine + bordereau.getDate().getYear();// year
		borderauLine = borderauLine + "00000000";

		String employmentNumber = bordereau.getClinique()
				.getEmployNumber();

		while (employmentNumber.length() < 12) {
			employmentNumber = "0" + employmentNumber;

		}

		borderauLine = borderauLine + employmentNumber;

		String factureNumber = bordereau.getFactures().size() + "";

		while (factureNumber.length() < 3) {
			factureNumber = "0" + factureNumber;

		}

		borderauLine = borderauLine + factureNumber;// nombre de facture par
													// borderau


		//todo nedd to verify is not null
		SeanceType seanceType = bordereau.getFactures().get(0).getSeances().first().getSeanceType();

		//todo remplacer par calcul de prix de seance et pas total individuel
		Long prixSeance = Long.parseLong(seanceType.getEXONERE())
				+ Long.parseLong(seanceType.getMSP())
				+ Long.parseLong(seanceType.getMTHTAXE())
				+ Long.parseLong(seanceType.getMTTVA());

		int SeanceNumber = getSeanceNumberInBorderau(bordereau);
		String prixTotal = prixSeance * SeanceNumber + "";
		while (prixTotal.length() < 10) {

			prixTotal = "0" + prixTotal;
		}

		borderauLine = borderauLine + prixTotal;// prix total des factures

		borderauLine = borderauLine + "00000000";// date d�but
		borderauLine = borderauLine + "00000000";// date fin

		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		sdf.applyPattern(NEW_FORMAT);


		String cliniqueDateFormat ="yyyyMMdd";

		DateTimeFormatter cliniqueDateFormatFormatter = DateTimeFormatter.ofPattern(cliniqueDateFormat);
		borderauLine = borderauLine + bordereau.getDate().format(cliniqueDateFormatFormatter);// date du
																// borderau
		String tva = (Long.parseLong(seanceType.getMTTVA()) * SeanceNumber)
				+ "";
		while (tva.length() < 9) {

			tva = "0" + tva;
		}

		borderauLine = borderauLine + tva;

		// added 2017 total horsTax
		String horsTaxe = (Long.parseLong(seanceType.getMTHTAXE()) * SeanceNumber)
				+ "";

		while (horsTaxe.length() < 10) {

			horsTaxe = "0" + horsTaxe;
		}
		borderauLine = borderauLine + horsTaxe;
		return borderauLine;
	}

	private int getSeanceNumberInBorderau(Bordereau bordereau) {
		int seanceNumber =0;
		for (Facture facture : bordereau.getFactures()
		){
			seanceNumber = seanceNumber + facture.getSeances().size();
		}
		return seanceNumber;
	}

	@Override
	public String factureLines(Bordereau bordereau) {
		String cliniqueDateFormat ="yyyyMMdd";

		DateTimeFormatter cliniqueDateFormatFormatter = DateTimeFormatter.ofPattern(cliniqueDateFormat);
		String borderauLines = "";
		for (Facture fact : bordereau.getFactures()){



		String borderauLine = "";
		borderauLine = "2"; // type facture

		borderauLine = borderauLine + bordereau.getDate().getYear();// year borderau

		String borderauNumber = bordereau.getNumber();

		while (borderauNumber.length() < 3) {

			borderauNumber = "0" + borderauNumber;
		}
		borderauLine = borderauLine + borderauNumber;
		
		
		//added  2017
		borderauLine = borderauLine+"02";
		
		borderauLine = borderauLine
				+ bordereau.getClinique().getCodeCentre();// code
																	// centre

		borderauLine = borderauLine + bordereau.getDate().getYear();



		String factNumber = "";

		factNumber = fact.getNumber();


		
		//modified 2017 5---->8
		while (factNumber.length() < 8) {

			factNumber = "0" + factNumber;
		}

		
		borderauLine = borderauLine + factNumber;

		
		borderauLine = borderauLine
				+ bordereau.getClinique().getCodeBureauxRegional();// code
																			// bureau
																			// r�gional
		borderauLine = borderauLine + "21";

		
		//added 2017
//		borderauLine = borderauLine + date.getYear();// year borderau

		// borderauLine=borderauLine+date.getYear()+"";
			//todo replace by real number depends on seance date
		borderauLine = borderauLine
				+ fact.getPatient().getPriseEnCharges().get(0).getNumber().split(" ")[1];

		String priseEnCharge = fact.getPatient().getPriseEnCharges().get(0).getNumber().split(" ")[2];

		while (priseEnCharge.length() < 6) {

			priseEnCharge = "0" + priseEnCharge;
		}
		borderauLine = borderauLine + priseEnCharge;
		
		
		//added 2017    if F0  NOTRE PATIENT
			//todo treate real case (if not local patient)
		borderauLine = borderauLine + "F0";
		
		
		
		
		String numAff1 = "00000000";
		String numAff2 = "00";

		
		
		//modified 2017 8----->10
		try {
			numAff1 = fact.getPatient().getNumAffiliation().split("-")[0];

			while (numAff1.length() < 10) {

				numAff1 = "0" + numAff1;
			}
		} catch (Exception e) {

		}
		try {
			numAff2 = fact.getPatient().getNumAffiliation().split("-")[1];

			while (numAff2.length() < 2) {

				numAff2 = "0" + numAff2;
			}
		} catch (Exception e) {

		}

		borderauLine = borderauLine + numAff1 + numAff2;


		String seanceNumber = fact.getSeances().size() + "";

		while (seanceNumber.length() < 3) {

			seanceNumber = "0" + seanceNumber;

		}
		borderauLine = borderauLine + seanceNumber;



		//todo nedd to verify is not null
		SeanceType seanceType = bordereau.getFactures().get(0).getSeances().first().getSeanceType();
		//todo fait le calcule reel de chaque seance
		Long prixttc = Long.parseLong(seanceType.getEXONERE())
				+ Long.parseLong(seanceType.getMSP()) + Long.parseLong(seanceType.getMTHTAXE())
				+ Long.parseLong(seanceType.getMTTVA());

		prixttc = prixttc * Long.parseLong(seanceNumber);
		String ppt = prixttc + "";

		while (ppt.length() < 10) {

			ppt = "0" + ppt;

		}
		borderauLine = borderauLine + ppt;

		//todo sort
		LocalDate datedebut = fact.getSeances().first().getDate();

		System.out.println("datedebut  == " + datedebut);
		LocalDate datefin = fact.getSeances().last().getDate();
		System.out.println("datefin  == " + datefin);



		String DateDebut = fact.getSeances().first().getDate().format(cliniqueDateFormatFormatter);
		borderauLine = borderauLine + DateDebut;

		String dateFin = fact.getSeances().last().getDate().format(cliniqueDateFormatFormatter);
		borderauLine = borderauLine + dateFin;

		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		sdf.applyPattern(NEW_FORMAT);



		borderauLine = borderauLine + bordereau.getDate().format(cliniqueDateFormatFormatter);// date du
																// borderau

		Long prixTVA = fact.getSeances().size() * Long.parseLong(seanceType.getMTTVA());

		String tva = prixTVA + "";

		while (tva.length() < 9) {

			tva = "0" + tva;

		}
		borderauLine = borderauLine + tva;

		Long prixHtax = fact.getSeances().size() * Long.parseLong(seanceType.getMTHTAXE());

		String horstax = prixHtax + "";

		while (horstax.length() < 10) {

			horstax = "0" + horstax;

		}
		borderauLine = borderauLine + horstax;
			borderauLines = borderauLines + "\n" +borderauLine;
		}

		return borderauLines;

	}

}
