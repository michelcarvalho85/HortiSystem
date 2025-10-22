// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.repository;

import com.HortiSystem.Sistema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import com.HortiSystem.Sistema.model.Produto;
import org.springframework.stereotype.Repository;

/**
 * REPOSITÓRIO DE PRODUTOS - ‘INTERFACE’ PARA OPERAÇÕES NO BANCO DE DADOS
 * Esta ‘interface’ gerencia a entidade Produto no banco de dados
 * Herda todos os métodos CRUD básicos do JpaRepository
 */
@Repository // Indica que esta é uma classe de repositório gerenciada pelo Spring
public interface ProdutoRepository extends JpaRepository<Produto , Long>{
    // NÃO PRECISA DECLARAR MÉTODOS - Herda automaticamente:
    // save(), findAll(), findById(), delete(), count(), etc.
}
