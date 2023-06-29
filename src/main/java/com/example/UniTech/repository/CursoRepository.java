package com.example.UniTech.repository;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Curso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface CursoRepository extends JpaRepository<Curso,Long> {
    @Query("FROM Curso WHERE ativo = true")
    List<Curso> findByAtivo();
    @Query("FROM Aluno WHERE curso = :curso AND ativo = true")
    List<Aluno> findCursoAtivoAluno(@Param("curso") final Curso curso);
    @Query("FROM Curso WHERE nome = :nome")
    List<Curso> findByNome(@Param("nome") final String nome);
    @Query("FROM Curso WHERE nome = :nome AND id != :id")
    List<Curso> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);
}
