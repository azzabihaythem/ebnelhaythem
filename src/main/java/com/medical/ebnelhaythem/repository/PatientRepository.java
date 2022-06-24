package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PatientRepository extends JpaRepository<Patient, Long> {
}
