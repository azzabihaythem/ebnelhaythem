package com.medical.ebnelhaythem.entity;

import lombok.Data;
import javax.persistence.*;

@Entity
@Table
@Data
public class Observation extends BaseEntity {

    @Column(name = "HEURE")
    private String heure;

    @Column(name = "TENSION_ARTERIELLE_S")
    private String tension_arterielle_s;

    @Column(name = "TENSION_ARTERIELLE_D")
    private String tension_arterielle_d;

    @Column(name = "HEPARINE")
    private String  heparine ;

    @Column(name = "DEBIT_SANG")
    private String  debit_sang ;

    @Column(name = "U_F")
    private String  uf ;

    @Column(name = "PV")
    private String  pv ;

    @Column(name = "PTM")
    private String  ptm ;

    @Column(name = "U_F_CUMULE")
    private String  uf_cumule ;

    @Column(name = "OBSERVATION")
    private String  observation;

    @ManyToOne(optional = true)
    @JoinColumn(name = "Bilan")
    private Bilan bilan;

}
