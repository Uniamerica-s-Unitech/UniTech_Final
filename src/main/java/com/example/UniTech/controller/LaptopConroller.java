package com.example.UniTech.controller;

import com.example.UniTech.entity.Laptop;
import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/laptop")
public class LaptopConroller {

    @Autowired
    private LaptopRepository laptopRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Laptop> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Laptop());
    }

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Laptop laptop = this.laptopRepository.findById(id).orElse(null);
        return laptop == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(laptop);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.laptopRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.laptopRepository.findByAtivo());
    }


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Laptop laptop){
        try{
            this.laptopRepository.save(laptop);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Laptop laptop
    ){
        final Laptop laptopBanco = this.laptopRepository.findById(id).orElse(null);
        if(laptopBanco == null || !laptopBanco.getId().equals(laptop.getId())){
            throw new RuntimeException("Nao foi possivel identificar o registro informado");
        }
        try{
            this.laptopRepository.save(laptop);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<?> delete(@RequestParam("id") final Long id) {
        final Laptop laptop = this.laptopRepository.findById(id).orElse(null);
        if (laptop == null){
            return ResponseEntity.badRequest().body("Laptop nao encontrado");
        }
        List<Ticket> laptopAtivo = this.laptopRepository.findLaptopAtivoTicket(laptop);
        if (!laptopAtivo.isEmpty()){
            if (laptop.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("ja ta inativo");
            }
            else {
                try {
                    laptop.setAtivo(Boolean.FALSE);
                    this.laptopRepository.save(laptop);
                    return ResponseEntity.ok("Laptop esta inativo");
                } catch (DataIntegrityViolationException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try{
            this.laptopRepository.delete(laptop);
            return ResponseEntity.ok("Laptop deletado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
