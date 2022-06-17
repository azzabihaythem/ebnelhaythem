package com.medical.ebnelhaythem.entity;

import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class Clinique extends BaseEntity{

    @Column
    private String nom;

    @Column
    private String adress;

    @Column
    private String tva;

    @Column
    private String registreDeCmmerce;

    @Column
    private String employNumber;

    @Column
    private String tel;

    @Column
    private String banqueName;

    @Column
    private String banqueNumber;

    @Column
    private String codeCentre;

    @Column
    private String codeBureauxRegional;

}
