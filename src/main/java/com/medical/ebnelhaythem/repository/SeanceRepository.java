package com.medical.ebnelhaythem.repository;


import com.medical.ebnelhaythem.entity.Seance;
import com.medical.ebnelhaythem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.SortedSet;

@RepositoryRestResource(exported = false)
public interface SeanceRepository extends  CrudRepository<Seance, Long>, JpaRepository<Seance, Long> , PagingAndSortingRepository<Seance, Long> {
    public SortedSet<Seance> findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(long patientId,
                                                                                        LocalDate startDate,
                                                                                        LocalDate endDate);
}
