package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedDate;

import javax.persistence.*;
import java.time.Instant;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class Clinique {

    @JsonProperty
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @JsonProperty
    @Column(updatable = false)
    @CreatedDate
    private Instant creationDate;

    @JsonProperty
    @Column
    @LastModifiedDate
    private Instant updateDate;

    @JsonProperty
    @Column
    private Boolean active;

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
