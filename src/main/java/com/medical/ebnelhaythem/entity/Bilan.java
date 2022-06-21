package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import javax.persistence.*;


@Entity
@Table
@Data
public class Bilan extends BaseEntity {

    @JsonProperty
    @Column(name = "GENERATEUR")
    private String generateur;

    @JsonProperty
    @Column(name = "FILTRE")
    private String filtre;

    @JsonProperty
    @Column(name = "POIDSSEC")
    private String poidsSec;

    @JsonProperty
    @Column(name = "UFT_DUREE")
    private String uftDuree;

    @JsonProperty
    @Column(name = "DEBUT_DE_DIALYSE")
    private String debutDeDialyse;

    @JsonProperty
    @Column(name = "POIDS_DEBUT")
    private String poidsDebut;

    @JsonProperty
    @Column(name = "PRISE_DE_POIDS")
    private String priseDePoids ;

    @JsonProperty
    @Column(name = "TENSION_ARTERIELLE_DEBUT_S")
    private String tensionarterielleDebutSystolique ;

    @JsonProperty
    @Column(name = "TENSION_ARTERIELLE_DEBUT_D")
    private String tensionarterielleDebutDiastolique ;

    @JsonProperty
    @Column(name = "FIN_DE_DIALYSE")
    private String finDeDialyse;

    @JsonProperty
    @Column(name = "POIDS_Fin")
    private String poidsFin;

    @JsonProperty
    @Column(name = "PERTE_DE_POIDS")
    private String perteDePoids ;

    @JsonProperty
    @Column(name = "TENSION_ARTERIELLE_FIN_S")
    private String tensionarterielleFinSystolique ;

    @JsonProperty
    @Column(name = "TENSION_ARTERIELLE_FIN_D")
    private String tensionarterielleFinDiastolique;

    @JsonProperty
    @Column(name = "HEPARINE")
    private Boolean  heparine ;

    @JsonProperty
    @Column(name = "TRAITEMENT")
    private String  traitement ;

    @JsonProperty
    @OneToOne
    private Seance seance;

    @JsonProperty
    @Column(name = "OBSERVATION_MEDICALES")
    private String  observationMedicales ;

    @JsonProperty
    @Column(name = "UFT")
    private String  uft ;

    @JsonProperty
    @Column(name = "OPTIONS")
    private String  options ;

}
