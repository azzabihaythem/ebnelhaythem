package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;

@Entity
@Table
@Data
public class Observation extends BaseEntity {

    @JsonProperty
    @Column(name = "HEURE")
    private String heure;

    @JsonProperty
    @Column(name = "TENSION_ARTERIELLE_S")
    private String tension_arterielle_s;

    @JsonProperty
    @Column(name = "TENSION_ARTERIELLE_D")
    private String tension_arterielle_d;

    @JsonProperty
    @Column(name = "HEPARINE")
    private String  heparine ;

    @JsonProperty
    @Column(name = "DEBIT_SANG")
    private String  debit_sang ;

    @JsonProperty
    @Column(name = "U_F")
    private String  uf ;

    @JsonProperty
    @Column(name = "PV")
    private String  pv ;

    @JsonProperty
    @Column(name = "PTM")
    private String  ptm ;

    @JsonProperty
    @Column(name = "U_F_CUMULE")
    private String  uf_cumule ;

    @JsonProperty
    @Column(name = "OBSERVATION")
    private String  observation;

    @JsonProperty
    @ManyToOne(optional = true)
    @JoinColumn(name = "Bilan")
    private Bilan bilan;

}
