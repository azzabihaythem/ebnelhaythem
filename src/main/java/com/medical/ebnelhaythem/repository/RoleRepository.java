package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;

@RepositoryRestResource(exported = false)
public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRole(String role);

}
