package com.example.UniTech.service;

import com.example.UniTech.entity.Aluno;
import com.example.UniTech.entity.Curso;
import com.example.UniTech.repository.CursoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class CursoService {
    @Autowired
    private CursoRepository cursoRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Curso curso){
        Assert.isTrue(curso.getNome() != null, "Nome invalido");
        Assert.isTrue(this.cursoRepository.findByNome(curso.getNome()).isEmpty(),"Já existe essa marca");
        this.cursoRepository.save(curso);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Curso curso, final Long id){
        final Curso cursoBanco = this.cursoRepository.findById(id).orElse(null);
        Assert.isTrue(this.cursoRepository.findByNomePut(curso.getNome(), id).isEmpty(), "Já existe essa marca");
        Assert.isTrue(cursoBanco != null || cursoBanco.getId().equals(curso.getId()), "Não foi possivel indenficar o registro no banco");
        this.cursoRepository.save(curso);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Curso curso){
        final Curso cursoBanco = this.cursoRepository.findById(curso.getId()).orElse(null);
        List<Aluno> cursoAtivo = this.cursoRepository.findCursoAtivoAluno(cursoBanco);
        if(cursoAtivo.isEmpty()){
            this.cursoRepository.delete(cursoBanco);
        } else{
            cursoBanco.setAtivo(Boolean.FALSE);
            this.cursoRepository.save(curso);
        }
    }
}
