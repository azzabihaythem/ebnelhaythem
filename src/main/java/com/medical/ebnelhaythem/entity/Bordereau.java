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
public class Bordereau extends BaseEntity{

    @Column
    @GeneratedValue(strategy= GenerationType.AUTO)
    private String borderauNumber;

    @JsonProperty
    @OneToMany
    private List<Facture> factures ;

    @JsonProperty
    @Column
    private Date date;
}
