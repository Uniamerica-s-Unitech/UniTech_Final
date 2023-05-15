package com.example.UniTech.controller;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.repository.AlunoRepository;
import com.example.UniTech.service.AlunoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value = "/api/aluno")
public class AlunoConroller {
    @Autowired
    private AlunoRepository alunoRepository;
    @Autowired
    private AlunoService alunoService;
    @GetMapping("/{id}")
    public ResponseEntity<Aluno> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Aluno());
    }
    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Aluno aluno = this.alunoRepository.findById(id).orElse(null);
        return aluno == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(aluno);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.alunoRepository.findAll());
    }
    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.alunoRepository.findByAtivo());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Aluno aluno){
        try{
            this.alunoService.cadastrar(aluno);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
    }
    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Aluno aluno
    ){
        try{
            this.alunoService.editar(aluno,id);
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
        final Aluno alunoBanco = this.alunoRepository.findById(id).orElse(null);
        this.alunoService.deletar(alunoBanco);
        return ResponseEntity.ok("Aluno deletado com Sucesso");
    }
}

