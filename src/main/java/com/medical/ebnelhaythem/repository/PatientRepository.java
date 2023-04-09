package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Patient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.util.List;

@RepositoryRestResource(exported = false)
public interface PatientRepository extends JpaRepository<Patient, Long> {

    List<Patient> findAllByUser_CliniqueId(Long cliniqueId);

}
