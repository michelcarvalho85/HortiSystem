package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class ProdutoService {

    @Autowired
    private ProdutoRepository repo;

    public List<Produto> listarTodos() {
        return repo.findAll();
    }

    public Optional<Produto> buscarPorId(Long id) {
        return repo.findById(id);
    }

    public Produto salvar(Produto p) {
        return repo.save(p);
    }

    public void deletar(Long id) {
        repo.deleteById(id);
    }
}
