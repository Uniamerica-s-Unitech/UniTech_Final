package com.example.UniTech.repository;

import com.example.UniTech.entity.Notebook;
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
    @Query("FROM Notebook WHERE modelo = :modelo AND ativo = true")
    List<Notebook> findModeloAtivoLaptop(@Param("modelo") final Modelo modelo);
    @Query("FROM Modelo WHERE nome = :nome")
    List<Modelo> findByNome(@Param("nome") final String nome);
    @Query("FROM Modelo WHERE nome = :nome AND id != :id")
    List<Modelo> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);
}
