package com.medical.ebnelhaythem.service;

import com.medical.ebnelhaythem.entity.Patient;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.Optional;

public interface PatientService {

    public Patient save(Patient patient);

    public Optional<Patient> findById(Long id);

    public void  deleteById(Long id);

    public void setPatientActive(Long id,Boolean active);

    public Page<Patient> findAll(Pageable pageable);

    public void updateAllPatientStatus(Boolean active,Long cliniqueId);
}
