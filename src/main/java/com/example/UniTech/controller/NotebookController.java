package com.example.UniTech.controller;
import com.example.UniTech.entity.Notebook;
import com.example.UniTech.repository.NotebookRepository;
import com.example.UniTech.service.NotebookService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value = "/api/notebook")
public class NotebookController {
    @Autowired
    private NotebookRepository notebookRepository;
    @Autowired
    private NotebookService notebookService;
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdRequest(@PathVariable("id") final Long id){

        final Notebook notebook = this.notebookRepository.findById(id).orElse(null);
        return notebook == null
                ? ResponseEntity.badRequest().body("Nenhum valor Encontrado")
                : ResponseEntity.ok(notebook);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.notebookRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.notebookRepository.findByAtivo());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Notebook notebook){
        try{
            this.notebookService.cadastrar(notebook);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
    @PutMapping("/{id}")
    public ResponseEntity<?> editar(@PathVariable("id") final Long id,
                                    @RequestBody final Notebook notebook
    ){
        try{
            this.notebookService.editar(notebook,id);
            return ResponseEntity.ok("Registro atualizado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
    @DeleteMapping("/{id}")
    public ResponseEntity<?> delete(@PathVariable("id") final Long id) {
        final Notebook notebookBanco = this.notebookRepository.findById(id).orElse(null);
        this.notebookService.deletar(notebookBanco);
        return ResponseEntity.ok("Notebook deletado com Sucesso");
    }
}
