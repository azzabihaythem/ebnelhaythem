package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

import javax.persistence.*;
import java.util.Date;
import java.util.List;

@Entity
@Table
@Data
public class Facture extends BaseEntity {

    @JsonProperty
    @Column
    private String number;

    @JsonProperty
    @ManyToOne
    private Patient patient;

    @JsonProperty
    @OneToMany
    private List<Seance> seances;

    @JsonProperty
    @Column
    private Date date;

    @JsonProperty
    @OneToMany
    private List<PdfUrl> pdfUrls;

}
