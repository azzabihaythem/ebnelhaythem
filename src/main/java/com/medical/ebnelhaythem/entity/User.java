package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;
import java.util.List;


@Entity
@Table
@Data
@EntityListeners(AuditingEntityListener.class)
public class User extends AbstractPersistable<Long> {


	@JsonProperty
	@JoinColumn(name="clinique_id")
	@ManyToOne
	private Clinique clinique;

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

	@JsonProperty
	@Column
	@CreatedDate
	private Date creationDate;

	@JsonProperty
	@Column
	@LastModifiedDate
	private Date updateDate;

	@JsonProperty
	@ManyToMany(cascade=CascadeType.ALL,fetch=FetchType.EAGER)
	@JoinTable(name="user_roles",
			joinColumns = {@JoinColumn(name="user_id", referencedColumnName="id")},
			inverseJoinColumns = {@JoinColumn(name="role_id", referencedColumnName="id")}
	)
	private List<Role> roles;


}
