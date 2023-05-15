package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "aluno", schema = "public")
public class Aluno extends AbstractEntity {
    @Getter @Setter
    @Column(name = "aluno_nome", nullable = false, length = 100)
    private String nome;
    @Getter @Setter
    @Column(name = "aluno_ra",nullable = false, unique = true,length = 8)
    private String ra;
    @Getter @Setter
    @Column(name = "aluno_rg",nullable = false,unique = true,length = 15)
    private String rg;
    @Getter @Setter
    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_curso",nullable = false)
    private Curso curso;
    @Getter @Setter
    @Enumerated(EnumType.STRING)
    @Column(name = "aluno_periodo",nullable = false,length = 20)
    private Periodo periodo;
}