package com.medical.ebnelhaythem.repository;



import com.medical.ebnelhaythem.entity.SeanceType;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface SeanceTypeRepository extends  CrudRepository<SeanceType, Long>, JpaRepository<SeanceType, Long> , PagingAndSortingRepository<SeanceType, Long> {



}
