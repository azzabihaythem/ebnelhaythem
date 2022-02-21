package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.util.Date;


@Entity
@Table
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class PriseEnCharge extends BaseEntity {

    @JsonProperty
    @Column(unique = true)
    private String number;

    @JsonProperty
    @Column
    private Date startDate;

    @JsonProperty
    @Column
    private Date endDate;

}
