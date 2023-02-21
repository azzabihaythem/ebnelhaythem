package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.FactureLastNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;


@RepositoryRestResource(exported = false)
public interface FactureLastNumberRepository extends JpaRepository<FactureLastNumber, Long> {

    FactureLastNumber findByCliniqueId(Long cliniqueId);

}
