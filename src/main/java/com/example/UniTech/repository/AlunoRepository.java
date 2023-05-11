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
    @Query("FROM Ticket WHERE ra = :ra AND ativo = true")
    List<Ticket> findAlunoAtivoTicket (@Param("ra") final Aluno aluno);
}
