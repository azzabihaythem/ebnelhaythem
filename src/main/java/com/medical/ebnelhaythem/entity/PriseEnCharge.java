package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.Date;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "clinique_id","number"})})
public class PriseEnCharge extends BaseEntity {

    @JsonProperty
    @JoinColumn(name="clinique_id", nullable=false)
    @ManyToOne
    private Clinique clinique;


    @JsonProperty
    @Column
    private String number;

    @JsonProperty
    @Column
    private LocalDate startDate;

    @JsonProperty
    @Column
    private LocalDate endDate;

}
