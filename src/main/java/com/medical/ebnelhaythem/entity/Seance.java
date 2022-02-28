package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;

@Entity
@Data
@Table(uniqueConstraints = {
        @UniqueConstraint(columnNames = { "patient_id","date"})})
public class Seance extends BaseEntity {

    @JsonProperty
    @ManyToOne
    @JoinColumn
    private Patient patient;

    @JsonProperty
    @Column
    private Date date;


}
