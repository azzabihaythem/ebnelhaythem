package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;
import javax.persistence.*;
import java.time.LocalDate;
import java.util.List;
import java.util.SortedSet;

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
    @OrderBy("Date DESC")
    private SortedSet<Seance> seances;

    //date should be exactly last day in the month
    @JsonProperty
    @Column
    private LocalDate date;

    @JsonProperty
    @OneToMany
    private List<PdfUrl> pdfUrls;

}
