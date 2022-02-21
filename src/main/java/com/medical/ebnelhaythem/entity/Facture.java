package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Facture extends BaseEntity {


    @Column
    private String number;

    @JsonProperty
    @ManyToOne
    private Patient patient;

    @JsonProperty
    @OneToMany
    private List<Seance> seances;

    @JsonProperty
    @Column
    private Date date;

}
