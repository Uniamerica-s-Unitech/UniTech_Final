package com.example.UniTech.controller;

import com.example.UniTech.entity.Periodo;
import com.example.UniTech.repository.PeriodoRepository;
import com.example.UniTech.service.PeriodoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

@Controller
@RequestMapping(value = "/api/periodo")
public class PeriodoController {
    @Autowired
    private PeriodoRepository periodoRepository;
    @Autowired
    private PeriodoService periodoService;
    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Periodo periodo = this.periodoRepository.findById(id).orElse(null);
        return periodo == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(periodo);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.periodoRepository.findAll());
    }
    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.periodoRepository.findByAtivo());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Periodo periodo){
        try{
            this.periodoService.cadastrar(periodo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Periodo periodo
    ){
        try{
            this.periodoService.editar(periodo,id);
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
        final Periodo periodoBanco = this.periodoRepository.findById(id).orElse(null);
        this.periodoService.deletar(periodoBanco);
        return ResponseEntity.ok("Marca deletada com Sucesso");
    }
}
