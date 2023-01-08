package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import java.util.List;
import java.util.Optional;

public interface PatientService {

    public Patient save(Patient patient);
    public Optional<Patient> findById(Long id);

    public void  deleteById(Long id);

    public Patient desactivatePatient(Long id);

    public Page<Patient> findAll(Pageable pageable);
}
