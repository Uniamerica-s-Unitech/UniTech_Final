package com.example.UniTech.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Entity
@Table(name = "modelo", schema = "public")
public class Modelo extends AbstractEntity {
    @Getter @Setter
    @Column(name = "modelo_nome", nullable = false, length = 100)
    private String nome;
    @Getter @Setter
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "marca_id", nullable = false)
    private Marca marca;
}
