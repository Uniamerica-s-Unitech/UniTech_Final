package com.example.UniTech.service;

import com.example.UniTech.entity.Marca;
import com.example.UniTech.entity.Modelo;
import com.example.UniTech.repository.MarcaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class MarcaService {
    @Autowired
    private MarcaRepository marcaRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Marca marca){
        Assert.isTrue(marca.getNome() != null, "Nome invalido");
        Assert.isTrue(this.marcaRepository.findByNome(marca.getNome()).isEmpty(), "Já existe esse marca");
        this.marcaRepository.save(marca);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Marca marca, final Long id){
        final Marca marcaBanco = this.marcaRepository.findById(id).orElse(null);
        Assert.isTrue(this.marcaRepository.findByNomePut(marca.getNome(),id).isEmpty(), "Já existe esse marca");
        Assert.isTrue(marcaBanco != null || marcaBanco.getId().equals(marca.getId()),"Não foi possivel indenficar o registro no banco");
        this.marcaRepository.save(marca);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Marca marca){
        final Marca marcaBanco = this.marcaRepository.findById(marca.getId()).orElse(null);
        List<Modelo> marcaAtivo = this.marcaRepository.findMarcaAtivoModelo(marcaBanco);
        if (marcaAtivo.isEmpty()){
            this.marcaRepository.delete(marcaBanco);
        }else {
            marcaBanco.setAtivo(Boolean.FALSE);
            this.marcaRepository.save(marca);
        }
    }
}
