package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table(name = "user")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends AbstractPersistable<Long> {

	@JsonProperty
	@Column(unique = true)
	private String login;

	@JsonProperty
	@Column
	private String password;

	@JsonProperty
	@Column
	private String firstName;

	@JsonProperty
	@Column
	private String lastName;

	@JsonProperty
	@Column
	private Date birthDate;

	//@ManyToMany
	//private List<Role> roles;


}
