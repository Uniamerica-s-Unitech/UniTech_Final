package com.example.UniTech.controller;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Curso;
import com.example.UniTech.repository.CursoRepository;
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

    @GetMapping("/{id}")
    public ResponseEntity<Curso> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Curso());
    }

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
            this.cursoRepository.save(curso);
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
        final Curso cursoBanco = this.cursoRepository.findById(id).orElse(null);
        if(cursoBanco == null || !cursoBanco.getId().equals(curso.getId())){
            throw new RuntimeException("Nao foi possivel identificar o registro informado");
        }
        try{
            this.cursoRepository.save(curso);
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
        final Curso curso = this.cursoRepository.findById(id).orElse(null);
        if (curso == null){
            return ResponseEntity.badRequest().body("Curso nao encontrado");
        }
        List<Aluno> cursoAtivo = this.cursoRepository.findCursoAtivoAluno(curso);
        if (!cursoAtivo.isEmpty()){
            if (curso.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("ja ta inativo");
            }
            else {
                try {
                    curso.setAtivo(Boolean.FALSE);
                    this.cursoRepository.save(curso);
                    return ResponseEntity.ok("Curso esta inativo");
                } catch (DataIntegrityViolationException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try{
            this.cursoRepository.delete(curso);
            return ResponseEntity.ok("Curso deletado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
