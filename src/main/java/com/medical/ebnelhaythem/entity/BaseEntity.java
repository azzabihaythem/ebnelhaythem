package com.medical.ebnelhaythem.entity;


import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.time.Instant;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
public abstract class BaseEntity {

    @JsonProperty
    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @JsonProperty
    @Column(updatable = false)
    @CreatedDate
    private Instant creationDate;

    @JsonProperty
    @Column
    @LastModifiedDate
    private Instant updateDate;

    @JsonProperty
    @Column
    @LastModifiedBy
    private String updateUser;

    @JsonProperty
    @Column(updatable = false)
    @CreatedBy
    private String createUser;

}
