package com.HortiSystem.Sistema.repository;

import com.HortiSystem.Sistema.model.Funcionario;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * REPOSITÓRIO DE FUNCIONÁRIOS - Interface para operações no banco de dados
 * Herda métodos CRUD básicos do JpaRepository e define métodos personalizados
 */
@Repository
public interface FuncionarioRepository extends JpaRepository<Funcionario, Long> {

    /**
     * BUSCA FUNCIONÁRIO POR CPF
     */
    Optional<Funcionario> findByCpf(String cpf);

    /**
     * BUSCA FUNCIONÁRIOS POR CARGO
     */
    List<Funcionario> findByCargo(String cargo);

    /**
     * BUSCA FUNCIONÁRIOS ATIVOS
     */
    List<Funcionario> findByAtivoTrue();

    /**
     * BUSCA FUNCIONÁRIOS INATIVOS
     */
    List<Funcionario> findByAtivoFalse();

    /**
     * VERIFICA SE CPF JÁ EXISTE (para validação de duplicidade)
     */
    boolean existsByCpf(String cpf);

    /**
     * BUSCA FUNCIONÁRIOS POR NOME (busca parcial)
     */
    @Query("SELECT f FROM Funcionario f WHERE LOWER(f.nome) LIKE LOWER(CONCAT('%', :nome, '%'))")
    List<Funcionario> findByNomeContainingIgnoreCase(String nome);
}