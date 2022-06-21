package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Clinique extends BaseEntity{

    @JsonProperty
    @Column
    private String nom;

    @JsonProperty
    @Column
    private String adress;

    @JsonProperty
    @Column
    private String tva;

    @JsonProperty
    @Column
    private String registreDeCmmerce;

    @JsonProperty
    @Column
    private String employNumber;

    @JsonProperty
    @Column
    private String tel;

    @JsonProperty
    @Column
    private String banqueName;

    @JsonProperty
    @Column
    private String banqueNumber;

    @JsonProperty
    @Column
    private String codeCentre;

    @JsonProperty
    @Column
    private String codeBureauxRegional;

}
