package com.example.UniTech.controller;

import com.example.UniTech.entity.Marca;
import com.example.UniTech.entity.Modelo;
import com.example.UniTech.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/marca")
public class MarcaConroller {

    @Autowired
    private MarcaRepository marcaRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Marca> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Marca());
    }

    @GetMapping
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
            this.marcaRepository.save(marca);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Marca marca
    ){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        if(marcaBanco == null || !marcaBanco.getId().equals(marca.getId())){
            throw new RuntimeException("Nao foi possivel identificar o registro informado");
        }
        try{
            this.marcaRepository.save(marca);
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
        final Marca marca = this.marcaRepository.findById(id).orElse(null);
        if (marca == null){
            return ResponseEntity.badRequest().body("Marca nao encontrado");
        }
        List<Modelo> marcaAtivo = this.marcaRepository.findMarcaAtivoModelo(marca);
        if (!marcaAtivo.isEmpty()){
            if (marca.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("ja ta inativo");
            }
            else {
                try {
                    marca.setAtivo(Boolean.FALSE);
                    this.marcaRepository.save(marca);
                    return ResponseEntity.ok("Marca esta inativo");
                } catch (DataIntegrityViolationException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try{
            this.marcaRepository.delete(marca);
            return ResponseEntity.ok("Marca deletado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
