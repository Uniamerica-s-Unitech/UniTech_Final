package com.example.UniTech.repository;

import com.example.UniTech.entity.Ticket;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TicketRepository extends JpaRepository<Ticket,Long> {
    @Query("FROM Ticket WHERE dataDevolucao = null")
    List<Ticket> findByAberta();
}
