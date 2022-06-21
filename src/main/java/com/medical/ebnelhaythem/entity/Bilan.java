package com.medical.ebnelhaythem.entity;

import lombok.Data;
import javax.persistence.*;


@Entity
@Table
@Data
public class Bilan extends BaseEntity {

    @Column(name = "GENERATEUR")
    private String generateur;

    @Column(name = "FILTRE")
    private String filtre;

    @Column(name = "POIDSSEC")
    private String poidsSec;

    @Column(name = "UFT_DUREE")
    private String uftDuree;

    @Column(name = "DEBUT_DE_DIALYSE")
    private String debutDeDialyse;

    @Column(name = "POIDS_DEBUT")
    private String poidsDebut;

    @Column(name = "PRISE_DE_POIDS")
    private String priseDePoids ;

    @Column(name = "TENSION_ARTERIELLE_DEBUT_S")
    private String tensionarterielleDebutSystolique ;

    @Column(name = "TENSION_ARTERIELLE_DEBUT_D")
    private String tensionarterielleDebutDiastolique ;

    @Column(name = "FIN_DE_DIALYSE")
    private String finDeDialyse;

    @Column(name = "POIDS_Fin")
    private String poidsFin;

    @Column(name = "PERTE_DE_POIDS")
    private String perteDePoids ;

    @Column(name = "TENSION_ARTERIELLE_FIN_S")
    private String tensionarterielleFinSystolique ;

    @Column(name = "TENSION_ARTERIELLE_FIN_D")
    private String tensionarterielleFinDiastolique;

    @Column(name = "HEPARINE")
    private Boolean  heparine ;

    @Column(name = "TRAITEMENT")
    private String  traitement ;

    @OneToOne
    private Seance seance;

    @Column(name = "OBSERVATION_MEDICALES")
    private String  observationMedicales ;

    @Column(name = "UFT")
    private String  uft ;

    @Column(name = "OPTIONS")
    private String  options ;

}
