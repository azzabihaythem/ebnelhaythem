package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.persistence.*;

@Entity
@Table
@Data
@AllArgsConstructor
@NoArgsConstructor
public class SeanceType extends BaseEntity {

    @JsonProperty
    @Column(name = "typeName")
    private String typeName;

    @JsonProperty
    @Column(name = "MTHTAXE")
    private String MTHTAXE;

    @JsonProperty
    @Column(name = "MTTVA")
    private String MTTVA;

    @JsonProperty
    @Column(name = "EXONERE")
    private String EXONERE;

    @JsonProperty
    @Column(name = "MSP")
    private String MSP;

}
