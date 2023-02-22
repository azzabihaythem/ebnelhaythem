package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.BorderauLastNumber;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface BorderauLastNumberRepository extends JpaRepository<BorderauLastNumber, Long> {

    BorderauLastNumber findByCliniqueId(Long cliniqueId);

}
