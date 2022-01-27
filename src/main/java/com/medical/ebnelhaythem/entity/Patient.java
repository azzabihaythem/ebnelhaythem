package com.medical.ebnelhaythem.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Patient extends AbstractPersistable<Long>{

    @JsonProperty
    @OneToOne
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
    @OneToMany
    private List<PriseEnCharge> priseEnCharges;




}
