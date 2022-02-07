package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@EntityListeners(AuditingEntityListener.class)
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

	@JsonProperty
	@Column
	private boolean active;

	@Column
	@CreatedDate
	private Date creationDate;

	@Column
	@LastModifiedDate
	private Date updateDate;

	//@ManyToMany
	//private List<Role> roles;


}
