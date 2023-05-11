package com.example.UniTech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "curso", schema = "public")
public class Curso extends AbstractEntity {
    @Getter
    @Setter
    @Column(name = "curso_nome", nullable = false, length = 100)
    private String nome;
}
