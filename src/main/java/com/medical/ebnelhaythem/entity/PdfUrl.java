package com.medical.ebnelhaythem.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

@Entity
@Table
@Data
public class PdfUrl extends BaseEntity{

    @JsonProperty
    @Column
    private String fileName;

    @JsonProperty
    @Column
    private String note;

    @JsonProperty
    @Column
    private String documentType;

}
