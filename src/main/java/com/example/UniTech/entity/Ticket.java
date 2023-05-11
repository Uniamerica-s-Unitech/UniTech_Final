package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Table(name = "ticket", schema = "public")
public class Ticket extends AbstractEntity {
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "ra_aluno",nullable = false, unique = true)
    private Aluno ra;
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "id_patrimonio",nullable = false, unique = true)
    private Laptop id_patrimonio;
    @Getter @Setter
    @Column(name = "entrega", nullable = false)
    private LocalDateTime dataEntrega;
    @Getter @Setter
    @Column(name = "devolusao")
    private LocalDateTime dataDevolucao;
    @Getter @Setter
    @Column(name = "comentario_devolucao",length = 500)
    private String comentarioDevolucao;
}
