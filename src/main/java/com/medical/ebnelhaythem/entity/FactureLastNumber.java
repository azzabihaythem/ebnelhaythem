package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class FactureLastNumber extends BaseEntity{

    @JsonProperty
    @JoinColumn(name="clinique_id", nullable=false,unique = true)
    @ManyToOne
    private Clinique clinique;

    @JsonProperty
    @Column
    private String number;
}
