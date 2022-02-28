package com.medical.ebnelhaythem.entity;

import javax.persistence.*;

import lombok.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Role {

	@Id
	@GeneratedValue (strategy = GenerationType.IDENTITY)
	private long id;

	private String role;

}
