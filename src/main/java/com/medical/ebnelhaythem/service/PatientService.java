package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;
import com.medical.ebnelhaythem.dto.PatientDto;


public interface PatientService {

    public Patient save(PatientDto patientModel);

}
