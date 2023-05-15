package com.example.UniTech.service;

import com.example.UniTech.entity.Laptop;
import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.LaptopRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class LaptopService {
    @Autowired
    private LaptopRepository laptopRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Laptop laptop){
        Assert.isTrue(laptop.getId_patrimonio() != null, "Id pateimonio invalido");
        Assert.isTrue(this.laptopRepository.findByIdPatrimonio(laptop.getId_patrimonio()).isEmpty(),"Já existe esse id");
        Assert.isTrue(laptop.getModelo() != null,"Modelo nao incontrado");
        this.laptopRepository.save(laptop);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Laptop laptop, final Long id){
        final Laptop laptopBanco = this.laptopRepository.findById(id).orElse(null);
        Assert.isTrue(this.laptopRepository.findByIdPatrimonioPut(laptop.getId_patrimonio(),id).isEmpty(),"Já existe esse id");
        Assert.isTrue(laptopBanco != null || laptopBanco.getId().equals(laptop.getId()), "Não foi possivel indenficar o registro no banco");
        Assert.isTrue(laptop.getModelo() != null,"Modelo nao incontrado");
        this.laptopRepository.save(laptop);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Laptop veiculo){
        final Laptop laptopBanco = this.laptopRepository.findById(veiculo.getId()).orElse(null);
        List<Ticket> laptopAtivo = this.laptopRepository.findLaptopAtivoTicket(laptopBanco);
        if(laptopAtivo.isEmpty()){
            this.laptopRepository.delete(laptopBanco);
        } else{
            laptopBanco.setAtivo(Boolean.FALSE);
            this.laptopRepository.save(veiculo);
            ResponseEntity.ok("Laptop ta em uso");
        }
    }
}
