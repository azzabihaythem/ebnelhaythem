package com.medical.ebnelhaythem.repository;

import com.medical.ebnelhaythem.entity.Role;
import org.springframework.data.jpa.repository.JpaRepository;


public interface RoleRepository extends JpaRepository<Role, Long>{

	Role findByRole(String role);

}
