package com.example.UniTech.controller;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/aluno")
public class AlunoConroller {
    @Autowired
    public AlunoRepository alunoRepository;

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
            this.alunoRepository.save(aluno);
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
        final Aluno alunoBanco = this.alunoRepository.findById(id).orElse(null);
        if(alunoBanco == null || !alunoBanco.getId().equals(aluno.getId())){
            throw new RuntimeException("Nao foi possivel identificar o registro informado");
        }
        try{
            this.alunoRepository.save(aluno);
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
        final Aluno aluno = this.alunoRepository.findById(id).orElse(null);
        if (aluno == null){
            return ResponseEntity.badRequest().body("Aluno nao encontrado");
        }
        List<Ticket> alunoAtivo = this.alunoRepository.findAlunoAtivoTicket(aluno);
        if (!alunoAtivo.isEmpty()){
            if (aluno.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("ja ta inativo");
            }
            else {
                try {
                    aluno.setAtivo(Boolean.FALSE);
                    this.alunoRepository.save(aluno);
                    return ResponseEntity.ok("Aluno esta inativo");
                } catch (DataIntegrityViolationException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try{
            this.alunoRepository.delete(aluno);
            return ResponseEntity.ok("Aluno deletado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
