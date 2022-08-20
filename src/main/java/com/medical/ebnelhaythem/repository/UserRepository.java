package com.medical.ebnelhaythem.repository;

import java.util.List;

import com.medical.ebnelhaythem.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.data.rest.core.annotation.RepositoryRestResource;
import org.springframework.stereotype.Repository;


@RepositoryRestResource(exported = false)
public interface UserRepository extends CrudRepository<User, Long>,
		JpaRepository<User, Long>, PagingAndSortingRepository<User, Long> {

	User findByLogin(String login);

//    List<User> findByRolesLike(String role);
	
	
}
