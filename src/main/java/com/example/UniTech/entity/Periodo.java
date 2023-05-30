package com.example.UniTech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

@Audited
@Entity
@AuditTable(value = "periodo_audit",schema = "audit")
@Table(name = "periodo", schema = "public")
public class Periodo extends AbstractEntity {
    @Getter
    @Setter
    @Column(name = "periodo_nome", nullable = false, length = 100)
    private String nome;
}
