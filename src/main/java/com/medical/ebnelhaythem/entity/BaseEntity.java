package com.medical.ebnelhaythem.entity;


import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.springframework.data.annotation.CreatedBy;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;
import org.springframework.data.jpa.domain.support.AuditingEntityListener;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
@EntityListeners(AuditingEntityListener.class)
@Data
@NoArgsConstructor
public abstract class BaseEntity extends AbstractPersistable<Long> {

    @Column(updatable = false)
    @CreatedDate
    private Date creationDate;

    @Column
    @LastModifiedDate
    private Date updateDate;

    @Column
    @LastModifiedBy
    private String updateUser;

    @Column(updatable = false)
    @CreatedBy
    private String createUser;

}
