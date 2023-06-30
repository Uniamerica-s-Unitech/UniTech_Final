package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@Entity
@AuditTable(value = "notebook_audit",schema = "audit")
@Table(name = "notebook", schema = "public")
public class Notebook extends AbstractEntity {
    @Getter @Setter
    @Column(name = "patrimonio",nullable = false, unique = true,length = 25)
    private String patrimonio;
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laptop_modelo",nullable = false)
    private Modelo modelo;
}