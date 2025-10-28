// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.repository;

import com.HortiSystem.Sistema.model.Usuario;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;

/**
 * REPOSITÓRIO DE USUÁRIOS - INTERFACE PARA OPERAÇÕES NO BANCO DE DADOS
 * Esta interface estende JpaRepository e herda automaticamente métodos CRUD
 * O Spring Data JPA implementa automaticamente os métodos declarados aqui
 */
public interface UsuarioRepository extends JpaRepository<Usuario, Long> {
    /**
     * MÉTODO PERSONALIZADO - BUSCA USUÁRIO PELO USERNAME
     * O Spring Data JPA implementa automaticamente este método baseado no nome
     * @param username - Nome de usuário para buscar
     * @return Optional<Usuario> - Container que pode ou não conter o usuário
     */
    Optional<Usuario> findByUsername(String username);
}

