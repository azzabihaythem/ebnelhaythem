package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PatientRepository extends JpaRepository<Patient, Long> {
}
