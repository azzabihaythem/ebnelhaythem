package com.medical.ebnelhaythem.repository;


import com.medical.ebnelhaythem.entity.Seance;
import org.apache.tomcat.jni.Local;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface SeanceRepository extends JpaRepository<Seance, Long> {

    public List<Seance> findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(String patientId,
                                                                                          LocalDate startDate,
                                                                                          LocalDate endDate);
}
