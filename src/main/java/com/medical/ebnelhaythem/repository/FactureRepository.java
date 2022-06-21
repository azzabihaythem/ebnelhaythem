package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Facture;
import org.springframework.data.jpa.repository.JpaRepository;

public interface FactureRepository extends JpaRepository<Facture, Long> {
}
