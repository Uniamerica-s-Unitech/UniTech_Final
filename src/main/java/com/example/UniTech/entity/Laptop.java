package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@Entity
@AuditTable(value = "laptop_audit",schema = "audit")
@Table(name = "laptop", schema = "public")
public class Laptop extends AbstractEntity {
    @Getter @Setter
    @Column(name = "id_patrimonio",nullable = false, unique = true,length = 25)
    private String id_patrimonio;
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "laptop_modelo",nullable = false)
    private Modelo modelo;
    @Getter @Setter
    @Column(name = "laptop_estado",length = 500)
    private String comentario;
}