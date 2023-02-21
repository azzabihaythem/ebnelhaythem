package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface FactureRepository extends JpaRepository<Facture, Long> {

    List<Facture> findByPatientIdAndDateGreaterThanEqualAndDateLessThanEqual(String patientId,
                                                                             LocalDate startDate,
                                                                             LocalDate endDate);

    Facture findByPatientIdAndDate(Long patientId,LocalDate date);

    Facture findTop1ByPatient_User_CliniqueIdOrderByIdDesc(Long cliniqueId);
}
