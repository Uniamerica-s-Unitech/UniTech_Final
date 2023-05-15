package com.example.UniTech.repository;

import com.example.UniTech.entity.Laptop;
import com.example.UniTech.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LaptopRepository extends JpaRepository<Laptop,Long> {
    @Query("FROM Laptop WHERE ativo = true")
    List<Laptop> findByAtivo();
    @Query("FROM Ticket WHERE id_patrimonio = :id_patrimonio AND ativo = true")
    List<Ticket> findLaptopAtivoTicket(@Param("id_patrimonio") final Laptop laptop);
    @Query("FROM Laptop WHERE id_patrimonio = :id_patrimonio")
    List<Laptop> findByIdPatrimonio(@Param("id_patrimonio") final String id_patrimonio);
    @Query("FROM Laptop WHERE id_patrimonio = :id_patrimonio AND id != :id")
    List<Laptop> findByIdPatrimonioPut(@Param("id_patrimonio") final String id_patrimonio, @Param("id") final Long id);
}
