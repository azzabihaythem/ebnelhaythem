package com.medical.ebnelhaythem.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Patient extends BaseEntity{

    @JsonProperty
    @OneToOne(cascade = {CascadeType.ALL})
    private User user;

    @JsonProperty
    @Column
    private String doit;

    @JsonProperty
    @Column
    private String affile;

    @JsonProperty
    @Column(unique = true)
    private String numAffiliation;

    @JsonProperty
    @OneToMany(cascade = {CascadeType.ALL})
    private List<PriseEnCharge> priseEnCharges;

    @JsonProperty
    @Column
    private Date desactivationDate;

    @JsonProperty
    @Column
    private boolean active;



}
