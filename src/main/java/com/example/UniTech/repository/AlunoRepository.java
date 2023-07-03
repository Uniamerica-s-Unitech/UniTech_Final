package com.example.UniTech.repository;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    @Query("FROM Aluno WHERE ativo = true")
    List<Aluno> findByAtivo();
    @Query("FROM Ticket WHERE alunoId = :alunoId AND ativo = true")
    List<Ticket> findAlunoAtivoTicket (@Param("alunoId") final Aluno aluno);
    @Query("FROM Aluno WHERE nome = :nome")
    List<Aluno> findByNome(@Param("nome") final String nome);
    @Query("FROM Aluno WHERE nome = :nome AND id != :id")
    List<Aluno> findByNomePut(@Param("nome") final String nome, @Param("id") final Long id);
    @Query("FROM Aluno WHERE ra = :ra")
    List<Aluno> findByRa(@Param("ra") final String ra);
    @Query("FROM Aluno WHERE ra = :ra AND id != :id")
    List<Aluno> findByRaPut(@Param("ra") final String cpf, @Param("id") final Long id);
    @Query("FROM Aluno WHERE rg = :rg")
    List<Aluno> findByRg(@Param("rg") final String rg);
    @Query("FROM Aluno WHERE rg = :rg AND id != :id")
    List<Aluno> findByRgPut(@Param("rg") final String rg, @Param("id") final Long id);
}
