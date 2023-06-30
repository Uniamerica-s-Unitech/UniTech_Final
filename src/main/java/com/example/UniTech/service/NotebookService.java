package com.example.UniTech.service;

import com.example.UniTech.entity.Notebook;
import com.example.UniTech.entity.Ticket;
import com.example.UniTech.repository.ModeloRepository;
import com.example.UniTech.repository.NotebookRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.Assert;

import java.util.List;
@Service
public class NotebookService {
    @Autowired
    private NotebookRepository notebookRepository;
    @Autowired
    private ModeloRepository modeloRepository;
    @Transactional(rollbackFor = Exception.class)
    public void cadastrar(final Notebook notebook){
        Assert.isTrue(notebook.getPatrimonio() != null, "Id pateimonio invalido");
        Assert.isTrue(this.notebookRepository.findByIdPatrimonio(notebook.getPatrimonio()).isEmpty(),"Já existe esse id");
        Assert.isTrue(notebook.getModelo() != null,"Modelo nao incontrado");
        Assert.isTrue(!this.modeloRepository.findById(notebook.getModelo().getId()).isEmpty(),"Modelo não existe");

        this.notebookRepository.save(notebook);
    }
    @Transactional(rollbackFor = Exception.class)
    public void editar(final Notebook notebook, final Long id){
        final Notebook notebookBanco = this.notebookRepository.findById(id).orElse(null);
        Assert.isTrue(this.notebookRepository.findByIdPatrimonioPut(notebook.getPatrimonio(),id).isEmpty(),"Já existe esse id");
        Assert.isTrue(notebookBanco != null || notebookBanco.getId().equals(notebook.getId()), "Não foi possivel indenficar o registro no banco");
        Assert.isTrue(notebook.getModelo() != null,"Modelo nao incontrado");
        this.notebookRepository.save(notebook);
    }
    @Transactional(rollbackFor = Exception.class)
    public void deletar(final Notebook veiculo){
        final Notebook notebookBanco = this.notebookRepository.findById(veiculo.getId()).orElse(null);
        List<Ticket> laptopAtivo = this.notebookRepository.findLaptopAtivoTicket(notebookBanco);
        if(laptopAtivo.isEmpty()){
            this.notebookRepository.delete(notebookBanco);
        } else{
            notebookBanco.setAtivo(Boolean.FALSE);
            this.notebookRepository.save(veiculo);
        }
    }
}
