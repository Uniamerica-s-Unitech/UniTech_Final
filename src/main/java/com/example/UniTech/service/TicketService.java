package com.example.UniTech.service;

import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

@Service
public class TicketService {
    @Autowired
    private TicketRepository ticketRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Ticket ticket){
        Assert.isTrue(ticket.getRa() != null, "Aluno não encontrado");
        Assert.isTrue(ticket.getId_patrimonio() != null, "Laptop não encontrado");
        Assert.isTrue(ticket.getDataEntrega() != null, "Entrega está invalida");
        this.ticketRepository.save(ticket);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Ticket ticket, final Long id){
        final Ticket ticketBanco = this.ticketRepository.findById(id).orElse(null);
        Assert.isTrue(ticketBanco != null || ticketBanco.getId().equals(ticket.getId()), "Não foi possivel identificar o registro informado.");
        Assert.isTrue(ticket.getRa() != null, "Aluno não encontrado");
        Assert.isTrue(ticket.getId_patrimonio() != null, "Laptop não encontrado");
        Assert.isTrue(ticket.getDataEntrega() != null, "Entrega está invalida");
        this.ticketRepository.save(ticket);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Ticket ticket){
        final Ticket ticketBanco = this.ticketRepository.findById(ticket.getId()).orElse(null);
        ticketBanco.setAtivo(Boolean.FALSE);
        this.ticketRepository.save(ticket);
    }
}
