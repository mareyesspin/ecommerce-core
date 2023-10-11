package com.techtrove.ecommerce.core.models.entity;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.Date;


@Data
@NoArgsConstructor
@AllArgsConstructor
@MappedSuperclass
@Inheritance(strategy = InheritanceType.TABLE_PER_CLASS)
public class EntityMasterAudit {
    @Column(name = "creationdate")
    @CreationTimestamp
    private Date creationDate;
    @Column(name = "creationuser")
    private String creationUser;
    @Column(name = "modificationdate")
    @UpdateTimestamp
    private Date modificationDate;
    @Column(name = "modificationuser")
    private String modificationUser;
}
