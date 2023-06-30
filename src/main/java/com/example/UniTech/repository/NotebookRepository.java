package com.example.UniTech.repository;

import com.example.UniTech.entity.Notebook;
import com.example.UniTech.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface NotebookRepository extends JpaRepository<Notebook,Long> {
    @Query("FROM Notebook WHERE ativo = true")
    List<Notebook> findByAtivo();
    @Query("FROM Ticket WHERE patrimonio = :patrimonio AND ativo = true")
    List<Ticket> findLaptopAtivoTicket(@Param("patrimonio") final Notebook notebook);
    @Query("FROM Notebook WHERE patrimonio = :patrimonio")
    List<Notebook> findByIdPatrimonio(@Param("patrimonio") final String patrimonio);
    @Query("FROM Notebook WHERE patrimonio = :patrimonio AND id != :id")
    List<Notebook> findByIdPatrimonioPut(@Param("patrimonio") final String patrimonio, @Param("id") final Long id);
}
