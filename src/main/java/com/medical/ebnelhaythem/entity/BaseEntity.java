package com.medical.ebnelhaythem.entity;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.annotation.CreatedDate;
import org.springframework.data.annotation.LastModifiedBy;
import org.springframework.data.annotation.LastModifiedDate;
import org.springframework.data.jpa.domain.AbstractPersistable;

import javax.persistence.*;
import java.util.Date;

@MappedSuperclass
public abstract class BaseEntity extends AbstractPersistable<Long> {

    @Column
    @CreatedDate
    private Date creationDate;

    @Column
    @LastModifiedDate
    private Date updateDate;

   // @Column
   // @LastModifiedBy
    //private User user;
}
