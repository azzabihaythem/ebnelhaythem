package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Observation;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ObservationRepository extends JpaRepository<Observation, Long> {
}
