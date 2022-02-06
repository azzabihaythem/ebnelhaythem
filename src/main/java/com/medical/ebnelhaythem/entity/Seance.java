package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.*;
import java.util.Date;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
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
