package com.example.UniTech.repository;

import com.example.UniTech.entity.Marca;
import com.example.UniTech.entity.Modelo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface MarcaRepository extends JpaRepository<Marca,Long> {
    @Query("FROM Marca WHERE ativo = true")
    List<Marca> findByAtivo();
    @Query("FROM Modelo WHERE marca = :marca AND ativo = true")
    List<Modelo> findMarcaAtivoModelo(@Param("marca") final Marca marca);
    @Query("FROM Marca WHERE nome = :nome")
    List<Marca> findByNome(@Param("nome") final String nome);
    @Query("FROM Marca WHERE nome = :nome AND id != :id")
    List<Marca> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);
}
