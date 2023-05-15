package com.example.UniTech.controller;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Curso;
import com.example.UniTech.repository.CursoRepository;
import com.example.UniTech.service.CursoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/curso")
public class CursoConroller {
    @Autowired
    private CursoRepository cursoRepository;
    @Autowired
    private CursoService cursoService;
    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Curso curso = this.cursoRepository.findById(id).orElse(null);
        return curso == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(curso);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.cursoRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.cursoRepository.findByAtivo());
    }


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Curso curso){
        try{
            this.cursoService.cadastrar(curso);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Curso curso
    ){
        try{
            this.cursoService.editar(curso,id);
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
        final Curso cursoBanco = this.cursoRepository.findById(id).orElse(null);
        this.cursoService.deletar(cursoBanco);
        return ResponseEntity.ok("Curso deletado com Sucesso");
    }
}
