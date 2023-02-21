package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.*;

@Entity
@Table
@Data
public class FactureLastNumber extends BaseEntity{

    @JsonProperty
    @JoinColumn(name="clinique_id", nullable=false,unique = true)
    @ManyToOne
    private Clinique clinique;

    @JsonProperty
    @Column
    private String number;
}
