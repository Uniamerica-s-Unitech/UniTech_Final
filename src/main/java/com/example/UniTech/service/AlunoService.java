package com.example.UniTech.service;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.AlunoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;

@Service
public class AlunoService {
    @Autowired
    private AlunoRepository alunoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Aluno aluno){
        Assert.isTrue(aluno.getNome() != null, "Nome invalido");
        Assert.isTrue(this.alunoRepository.findByNome(aluno.getNome()).isEmpty(),"Já existe esse nome");
        Assert.isTrue(aluno.getRa() != null, "Ra invalida");
        Assert.isTrue(this.alunoRepository.findByRa(aluno.getRa()).isEmpty(),"Já existe essa ra");
        Assert.isTrue(aluno.getRg() != null, "Rg invalido");
        Assert.isTrue(this.alunoRepository.findByRg(aluno.getRg()).isEmpty(),"Já existe esse rg");
        String raValido = "\\d{6}";
        String rgValido = "\\d{3}\\.?\\d{3}\\.?\\d{3}-?\\d{2}";
        Assert.isTrue(aluno.getRa().matches(raValido),"Formato da ra invalido");
        Assert.isTrue(aluno.getRg().matches(rgValido),"Formato do rg invalido");
        Assert.isTrue(aluno.getCurso() != null,"Curso nao incontrado");
        Assert.isTrue(aluno.getPeriodo() != null,"Periodo nao existe");
        this.alunoRepository.save(aluno);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Aluno aluno, final Long id){
        final Aluno alunoBanco = this.alunoRepository.findById(id).orElse(null);
        Assert.isTrue(this.alunoRepository.findByNomePut(aluno.getNome(), id).isEmpty(), "Já existe esse nome");
        Assert.isTrue(this.alunoRepository.findByRaPut(aluno.getRa(),id).isEmpty(),"Já existe essa ra");
        Assert.isTrue(this.alunoRepository.findByRgPut(aluno.getRg(),id).isEmpty(),"ja existe esse rg");
        String raValido = "\\d{6}";
        String rgValido = "\\d{3}\\.?\\d{3}\\\\.?\\d{3}-?\\d{2}";
        Assert.isTrue(aluno.getRa().matches(raValido),"Formato da ra invalido");
        Assert.isTrue(aluno.getRg().matches(rgValido),"Formato do rg invalido");
        Assert.isTrue(aluno.getCurso() != null,"Curso nao incontrado");
        Assert.isTrue(aluno.getPeriodo() != null,"Periodo nao existe");
        Assert.isTrue(alunoBanco != null || alunoBanco.getId().equals(aluno.getId()), "Não foi possivel indenficar o registro no banco");
        this.alunoRepository.save(aluno);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Aluno aluno){
        final Aluno alunoBanco = this.alunoRepository.findById(aluno.getId()).orElse(null);
        List<Ticket> alunoAtivo = this.alunoRepository.findAlunoAtivoTicket(alunoBanco);
        if(alunoAtivo.isEmpty()){
            this.alunoRepository.delete(alunoBanco);
        } else{
            alunoBanco.setAtivo(Boolean.FALSE);
            this.alunoRepository.save(aluno);
        }
    }
}