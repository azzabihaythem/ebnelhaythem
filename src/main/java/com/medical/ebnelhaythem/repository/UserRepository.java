package com.medical.ebnelhaythem.repository;

import java.util.List;

import com.medical.ebnelhaythem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;


@Repository
public interface UserRepository extends CrudRepository<User, Long>,
		JpaRepository<User, Long> {

	User findByUsernameLike(String username);

//    List<User> findByRolesLike(String role);
	
	
}
