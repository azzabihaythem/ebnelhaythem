package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.List;


@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractPersistable<Long> {

	@JsonProperty("username")
	@Column(name = "USER_NAME", unique = true)
	private String username;

	@JsonProperty("password")
	@Column(name = "PASSWORD")
	private String password;

	//@ManyToMany
	//private List<Role> roles;


}
