package com.medical.ebnelhaythem.entity;


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

    @Id
    @GeneratedValue (strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false)
    @CreatedDate
    private Instant creationDate;

    @Column
    @LastModifiedDate
    private Instant updateDate;

    @Column
    @LastModifiedBy
    private String updateUser;

    @Column(updatable = false)
    @CreatedBy
    private String createUser;

}
