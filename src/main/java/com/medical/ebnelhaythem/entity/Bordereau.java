package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;

@Entity
@Table
@Data
public class Bordereau extends BaseEntity{

    @JsonProperty
    @JoinColumn(name="clinique_id", nullable=false)
    @ManyToOne
    private Clinique clinique;

    @JsonProperty
    @Column
    private String number;

    @JsonProperty
    @OneToMany
    private List<Facture> factures ;

    @JsonProperty
    @Column
    private LocalDate date;

    @JsonProperty
    @OneToMany
    private List<PdfUrl> pdfUrls;

}
