package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.jpa.domain.AbstractPersistable;

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
public class PriseEnCharge extends AbstractPersistable<Long> {

    @JsonProperty
    @Column(unique = true)
    private String numeroPriseEnCharge;

    @JsonProperty
    @Column
    private Date dateDebut;

    @JsonProperty
    @Column
    private Date dateFin;

}
