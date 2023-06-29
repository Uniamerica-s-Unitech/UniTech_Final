package com.example.UniTech.controller;
import com.example.UniTech.entity.Laptop;
import com.example.UniTech.repository.LaptopRepository;
import com.example.UniTech.service.LaptopService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
@Controller
@RequestMapping(value = "/api/laptop")
public class LaptopController {
    @Autowired
    private LaptopRepository laptopRepository;
    @Autowired
    private LaptopService laptopService;
    @GetMapping("/{id}")
    public ResponseEntity<?> findByIdRequest(@PathVariable("id") final Long id){

        final Laptop laptop = this.laptopRepository.findById(id).orElse(null);
        return laptop == null
                ? ResponseEntity.badRequest().body("Nenhum valor Encontrado")
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
            this.laptopService.cadastrar(laptop);
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
                                    @RequestBody final Laptop laptop
    ){
        try{
            this.laptopService.editar(laptop,id);
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
        final Laptop laptopBanco = this.laptopRepository.findById(id).orElse(null);
        this.laptopService.deletar(laptopBanco);
        return ResponseEntity.ok("Laptop deletado com Sucesso");
    }
}
