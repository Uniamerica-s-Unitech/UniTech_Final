package com.example.UniTech.repository;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Periodo;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface PeriodoRepository extends JpaRepository<Periodo,Long> {
    @Query("FROM Periodo WHERE ativo = true")
    List<Periodo> findByAtivo();
    @Query("FROM Aluno WHERE periodo = :periodo AND ativo = true")
    List<Aluno> findPeriodoAtivoAluno(@Param("periodo") final Periodo periodo);
    @Query("FROM Periodo WHERE nome = :nome")
    List<Periodo> findByNome(@Param("nome") final String nome);
    @Query("FROM Periodo WHERE nome = :nome AND id != :id")
    List<Periodo> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);
}
