package com.example.UniTech.controller;

import com.example.UniTech.entity.Laptop;
import com.example.UniTech.entity.Modelo;
import com.example.UniTech.repository.ModeloRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@RequestMapping(value = "/api/modelo")
public class ModeloConroller {
    @Autowired
    private ModeloRepository modeloRepository;

    @GetMapping("/{id}")
    public ResponseEntity<Modelo> findByIdPath(@PathVariable("id") final Long id){
        return ResponseEntity.ok(new Modelo());
    }

    @GetMapping
    public ResponseEntity<?> findById(@RequestParam("id") final Long id){
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);
        return modelo == null
                ? ResponseEntity.badRequest().body("Nenhom valor encontrado.")
                : ResponseEntity.ok(modelo);
    }
    @GetMapping("/lista")
    public ResponseEntity<?> listaCompleta(){
        return ResponseEntity.ok(this.modeloRepository.findAll());
    }

    @GetMapping("/ativo")
    public ResponseEntity<?> findByAtivo(){
        return ResponseEntity.ok(this.modeloRepository.findByAtivo());
    }


    @PostMapping
    public ResponseEntity<?> cadastrar(@RequestBody final Modelo modelo){
        try{
            this.modeloRepository.save(modelo);
            return ResponseEntity.ok("Registro cadastrado com sucesso");
        }
        catch (DataIntegrityViolationException e){
            return ResponseEntity.internalServerError().body("Error: "+ e.getCause().getCause().getMessage());
        }
    }

    @PutMapping
    public ResponseEntity<?> editar(@RequestParam("id") final Long id,
                                    @RequestBody final Modelo modelo
    ){
        final Modelo modeloBanco = this.modeloRepository.findById(id).orElse(null);
        if(modeloBanco == null || !modeloBanco.getId().equals(modelo.getId())){
            throw new RuntimeException("Nao foi possivel identificar o registro informado");
        }
        try{
            this.modeloRepository.save(modelo);
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
        final Modelo modelo = this.modeloRepository.findById(id).orElse(null);
        if (modelo == null){
            return ResponseEntity.badRequest().body("Modelo nao encontrado");
        }
        List<Laptop> modeloAtivo = this.modeloRepository.findModeloAtivoLaptop(modelo);
        if (!modeloAtivo.isEmpty()){
            if (modelo.getAtivo().equals(Boolean.FALSE)){
                return ResponseEntity.ok("ja ta inativo");
            }
            else {
                try {
                    modelo.setAtivo(Boolean.FALSE);
                    this.modeloRepository.save(modelo);
                    return ResponseEntity.ok("Modelo esta inativo");
                } catch (DataIntegrityViolationException e) {
                    return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
                }
                catch (RuntimeException e){
                    return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
                }
            }
        }
        try{
            this.modeloRepository.delete(modelo);
            return ResponseEntity.ok("Modelo deletado com Sucesso");
        }
        catch (DataIntegrityViolationException e) {
            return ResponseEntity.internalServerError().body("Error: " + e.getCause().getCause().getMessage());
        }
        catch (RuntimeException e){
            return ResponseEntity.internalServerError().body("Error: " + e.getMessage());
        }
    }
}
