package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Bordereau;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

import java.time.LocalDate;
import java.util.List;

@RepositoryRestResource(exported = false)
public interface BordereauRepository extends JpaRepository<Bordereau, Long> {

    Bordereau findByDateAndCliniqueId(LocalDate endDate, long cliniqueId);

}
