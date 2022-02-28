package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Bordereau extends BaseEntity{

    @Column
    private String number;

    @JsonProperty
    @OneToMany
    private List<Facture> factures ;

    @JsonProperty
    @Column
    private Date date;
}
