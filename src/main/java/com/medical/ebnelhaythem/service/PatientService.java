package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;

import java.util.List;
import java.util.Optional;

public interface PatientService {

    public List<Patient> getAllPatient() ;
    public Patient save(Patient patient);
    public Optional<Patient> findById(Long id);
    public Optional<Patient> findByUserId(Long userId);

    public void  deleteById(Long id);

    public Patient desactivatePatient(Long id);
}
