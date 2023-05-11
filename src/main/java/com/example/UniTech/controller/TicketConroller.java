package com.example.UniTech.controller;

import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.TicketRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/ticket")
public class TicketConroller {
    @Autowired
    private TicketRepository ticketRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Ticket> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Ticket());
    }

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id) {
        final Ticket ticket = this.ticketRepository.findById(id).orElse(null);
        return ticket == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(ticket);
    }

    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta() {
        return ResponseEntity.ok(this.ticketRepository.findAll());
    }

    @GetMapping("/aberta")
    public ResponseEntity<?> findByAberta() {
        return ResponseEntity.ok(this.ticketRepository.findByAberta());
    }

    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Ticket ticket) {
        try {
            this.ticketRepository.save(ticket);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Ticket ticket
    ) {
        final Ticket ticketBanco = this.ticketRepository.findById(id).orElse(null);
        if (ticketBanco == null || !ticketBanco.getId().equals(ticket.getId())) {
            throw new RuntimeException("Nao foi possivel identificar o registro informado");
        }
        try {
            this.ticketRepository.save(ticket);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        } catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        } catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") final Long id) {
        final Ticket ticket = this.ticketRepository.findById(id).orElse(null);
        try {
            ticket.setAtivo(Boolean.FALSE);
            this.ticketRepository.save(ticket);
            return ResponseEntity.ok("Ticket esta inativo");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
