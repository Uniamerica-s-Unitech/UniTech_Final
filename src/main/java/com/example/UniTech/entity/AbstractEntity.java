package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

@MappedSuperclass
public abstract class AbstractEntity {
    @Id
    @Getter
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;
    @Getter @Setter
    @Column(name = "cadastro")
    private LocalDateTime cadastro;
    @Getter @Setter
    @Column(name = "atualizacao")
    private LocalDateTime atualizacao;
    @Getter @Setter
    @Column(name = "ativo")
    private Boolean ativo;
    @PrePersist
    private void prePersist(){
        this.cadastro = LocalDateTime.now();
        this.ativo = true;
    }
    @PreUpdate
    private void preUpdaete(){
        this.atualizacao = LocalDateTime.now();
    }
}