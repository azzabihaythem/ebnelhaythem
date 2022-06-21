package com.medical.ebnelhaythem.entity;

import javax.persistence.*;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	@JsonProperty
	private long id;

	@JsonProperty
	private String role;

}
