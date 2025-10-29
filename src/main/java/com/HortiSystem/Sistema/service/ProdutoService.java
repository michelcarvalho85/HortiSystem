package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.repository.ProdutoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
<<<<<<< HEAD
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
=======

/**
 * CAMADA DE SERVIÇO - CONTÉM A LÓGICA DE NEGÓCIO
 * Esta classe atua como intermediária entre o Controller e o Repository
 * Implementa as regras de negócio e operações sobre os produtos
 */
@Service // Indica que esta é uma classe de serviço gerenciada pelo Spring
public class ProdutoService {

    // INJEÇÃO DO REPOSITÓRIO - Spring injeta automaticamente
    @Autowired
    private ProdutoRepository repository;



    /**
     * MÉTODO: SALVAR PRODUTO
     * FUNÇÃO: Salva ou atualiza um produto no banco de dados
     * @param produto - Objeto produto a ser salvo
     * @return Produto salvo (com ID gerado)
     */
    public Produto salvar(Produto produto){
        return repository.save(produto); // Delega a operação para o repository
    }



    /**
     * MÉTODO: LISTAR TODOS OS PRODUTOS
     * FUNÇÃO: Recupera todos os produtos do banco de dados
     * @return Lista de todos os produtos
     */
    public List<Produto> listarTodos() {
        return repository.findAll(); // Delega a operação para o repository
    }
}
>>>>>>> 2792662d5b50809780d28e9605544be4100c1603
