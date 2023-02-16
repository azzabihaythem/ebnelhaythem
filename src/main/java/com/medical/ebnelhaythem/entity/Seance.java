package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;

@Entity
@Data
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
    private LocalDate date;

    @JsonProperty
    @ManyToOne
    private SeanceType seanceType;

}
