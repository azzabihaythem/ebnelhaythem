package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;

import java.util.Optional;

public interface PatientService {

    public Patient save(Patient patient);
    public Optional<Patient> findById(Long id);

    public void  deleteById(Long id);

    public Patient desactivatePatient(Long id);
}
