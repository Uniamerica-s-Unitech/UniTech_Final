package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.hibernate.envers.AuditTable;
import org.hibernate.envers.Audited;

import java.time.LocalDateTime;
@Audited
@Entity
@AuditTable(value = "ticket_audit",schema = "audit")
@Table(name = "ticket", schema = "public")
public class Ticket extends AbstractEntity {
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ra_aluno",nullable = false, unique = true)
    private Aluno alunoId;
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patrimonio",nullable = false, unique = true)
    private Notebook notebookId;
    @Getter @Setter
    @Column(name = "entrega", nullable = false)
    private LocalDateTime dataEntrega;
    @Getter @Setter
    @Column(name = "devolusao")
    private LocalDateTime dataDevolucao;

}
