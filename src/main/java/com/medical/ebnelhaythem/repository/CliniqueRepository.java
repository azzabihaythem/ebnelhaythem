package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Clinique;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface CliniqueRepository extends JpaRepository<Clinique, Long> {

    Clinique findById(long id);
}
