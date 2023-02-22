package com.medical.ebnelhaythem.service;

import java.text.SimpleDateFormat;
import java.time.LocalDate;
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
		SeanceType seanceType = bordereau.getFactures().get(0).getSeances().get(0).getSeanceType();

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

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, bordereau.getDate().getMonth().getValue());
		cal.set(Calendar.YEAR,  bordereau.getDate().getYear());
		cal.set(Calendar.DAY_OF_MONTH, 1);// This is necessary to get proper
											// results
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.getTime();

		borderauLine = borderauLine + sdf.format(cal.getTime());// date du
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
				+ borderauLine
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

		// String factureNumber = factureService.findByMonthAndYear(
		// date.getMonth() + "", date.getYear() + "").size()
		// + "";


		String seanceNumber = fact.getSeances().size() + "";

		while (seanceNumber.length() < 3) {

			seanceNumber = "0" + seanceNumber;

		}
		borderauLine = borderauLine + seanceNumber;



		//todo nedd to verify is not null
		SeanceType seanceType = bordereau.getFactures().get(0).getSeances().get(0).getSeanceType();
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
		LocalDate datedebut = fact.getSeances().get(0).getDate();

		System.out.println("datedebut  == " + datedebut);
		LocalDate datefin = fact.getSeances().get(fact.getSeances().size() - 1).getDate();
		System.out.println("datefin  == " + datefin);

		//todo verify format
		String DateDebutDay = datedebut + "";
		while (DateDebutDay.length() < 2) {
			DateDebutDay = "0" + DateDebutDay;
		}
		System.out.println("DateDebutDay  == " + DateDebutDay);
		String DateDebutMonth = (datedebut.getMonth().getValue() + 1) + "";
		while (DateDebutMonth.length() < 2) {
			DateDebutMonth = "0" + DateDebutMonth;
		}

		String DateDebut = bordereau.getDate().getYear() + DateDebutMonth + DateDebutDay;
		borderauLine = borderauLine + DateDebut;
		//todo verify format
		String DateFinDay = datefin+ "";
		while (DateFinDay.length() < 2) {
			DateFinDay = "0" + DateFinDay;
		}
		System.out.println("DateFinDay  == " + DateFinDay);
		String DateFinMonth = (datefin.getMonth().getValue() + 1) + "";
		while (DateFinMonth.length() < 2) {
			DateFinMonth = "0" + DateFinMonth;
		}

		String DateFin = bordereau.getDate().getYear() + DateFinMonth + DateFinDay;
		borderauLine = borderauLine + DateFin;

		final String OLD_FORMAT = "yyyy-MM-dd";
		final String NEW_FORMAT = "yyyyMMdd";
		SimpleDateFormat sdf = new SimpleDateFormat(OLD_FORMAT);
		sdf.applyPattern(NEW_FORMAT);

		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.MONTH, bordereau.getDate().getMonth().getValue());
		cal.set(Calendar.YEAR, bordereau.getDate().getYear());
		cal.set(Calendar.DAY_OF_MONTH, 1);// This is necessary to get proper
											// results
		cal.set(Calendar.DATE, cal.getActualMaximum(Calendar.DATE));
		cal.getTime();

		borderauLine = borderauLine + sdf.format(cal.getTime());// date du
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
