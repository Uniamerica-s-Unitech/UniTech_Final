package com.example.UniTech.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "marca", schema = "public")
public class Marca extends AbstractEntity {
    @Getter @Setter
    @Column(name = "marca_nome", nullable = false, length = 100)
    private String nome;
}