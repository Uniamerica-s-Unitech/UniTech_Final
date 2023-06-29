package com.example.UniTech.controller;
import com.example.UniTech.entity.Marca;
import com.example.UniTech.repository.MarcaRepository;
import com.example.UniTech.service.MarcaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value = "/api/marca")
public class MarcaController {
    @Autowired
    private MarcaRepository marcaRepository;
    @Autowired
    private MarcaService marcaService;
    @GetMapping("/{id}")
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Marca marca = this.marcaRepository.findById(id).orElse(null);
        return marca == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(marca);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.marcaRepository.findAll());
    }
    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.marcaRepository.findByAtivo());
    }
    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Marca marca){
        try{
            this.marcaService.cadastrar(marca);
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
                                    @RequestBody final Marca marca
    ){
        try{
            this.marcaService.editar(marca,id);
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
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        this.marcaService.deletar(marcaBanco);
        return ResponseEntity.ok("Marca deletada com Sucesso");
    }
}