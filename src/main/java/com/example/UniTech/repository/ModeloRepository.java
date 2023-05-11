package com.example.UniTech.repository;

import com.example.UniTech.entity.Laptop;
import com.example.UniTech.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ModeloRepository extends JpaRepository<Modelo,Long> {
    @Query("FROM Modelo WHERE ativo = true")
    List<Modelo> findByAtivo();
    @Query("FROM Laptop WHERE modelo = :modelo AND ativo = true")
    List<Laptop> findModeloAtivoLaptop(@Param("modelo") final Modelo modelo);
}
