package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class User extends BaseEntity {

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

	@JsonProperty
	@Column
	private boolean active;

	//@ManyToMany
	//private List<Role> roles;


}
