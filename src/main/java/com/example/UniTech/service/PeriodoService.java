package com.example.UniTech.service;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Periodo;
import com.example.UniTech.repository.PeriodoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class PeriodoService {
    @Autowired
    private PeriodoRepository periodoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Periodo periodo){
        Assert.isTrue(periodo.getNome() != null, "Nome invalido");
        Assert.isTrue(this.periodoRepository.findByNome(periodo.getNome()).isEmpty(), "Já existe esse periodo");
        this.periodoRepository.save(periodo);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Periodo periodo, final Long id){
        final Periodo periodoBanco = this.periodoRepository.findById(id).orElse(null);
        Assert.isTrue(this.periodoRepository.findByNomePut(periodo.getNome(),id).isEmpty(), "Já existe esse marca");
        Assert.isTrue(periodoBanco != null || periodoBanco.getId().equals(periodo.getId()),"Não foi possivel indenficar o registro no banco");
        this.periodoRepository.save(periodo);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Periodo periodo){
        final Periodo periodoBanco = this.periodoRepository.findById(periodo.getId()).orElse(null);
        List<Aluno> periodoAtivo = this.periodoRepository.findPeriodoAtivoAluno(periodoBanco);
        if (periodoAtivo.isEmpty()){
            this.periodoRepository.delete(periodoBanco);
        }else {
            periodoBanco.setAtivo(Boolean.FALSE);
            this.periodoRepository.save(periodo);
        }
    }
}
