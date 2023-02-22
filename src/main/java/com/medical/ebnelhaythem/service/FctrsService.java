package com.medical.ebnelhaythem.service;

import java.util.Date;


import com.medical.ebnelhaythem.entity.Bordereau;

public interface FctrsService {

	public String createTextFile(Bordereau bordereau);
	public String borderauLine(Bordereau bordereau);
	public String factureLines(Bordereau bordereau);


	
}
