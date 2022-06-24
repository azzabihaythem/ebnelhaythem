package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.PriseEnCharge;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface PriseEnChargeRepository extends JpaRepository<PriseEnCharge, Long> {
}
