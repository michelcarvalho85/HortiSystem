package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.Funcionario;
import com.HortiSystem.Sistema.repository.FuncionarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

/**
 * SERVIÇO DE FUNCIONÁRIOS - Contém a lógica de negócio para operações com funcionários
 * Atua como intermediário entre o Controller e o Repository
 */
@Service
public class FuncionarioService {

    @Autowired
    private FuncionarioRepository repository;

    /**
     * SALVAR FUNCIONÁRIO - Valida e salva um novo funcionário
     * ✅ CORRIGIDO: Melhor tratamento de erros
     */
    public Funcionario salvar(Funcionario funcionario) {
        System.out.println("🔍 Validando funcionário: " + funcionario.getNome());

        // Valida dados antes de salvar
        if (!funcionario.validarDados()) {
            throw new IllegalArgumentException("Dados do funcionário são inválidos. Verifique os campos.");
        }

        // Formata CPF e telefone antes de salvar
        funcionario.setCpf(funcionario.getCpfFormatado());
        funcionario.setTelefone(funcionario.getTelefoneFormatado());

        // Verifica se CPF já existe
        if (repository.existsByCpf(funcionario.getCpf())) {
            throw new IllegalArgumentException("CPF já cadastrado no sistema: " + funcionario.getCpf());
        }

        try {
            Funcionario salvo = repository.save(funcionario);
            System.out.println("✅ Funcionário salvo com sucesso: " + salvo.getNome());
            return salvo;
        } catch (Exception e) {
            System.out.println("❌ Erro ao salvar funcionário: " + e.getMessage());
            throw new RuntimeException("Erro ao salvar funcionário: " + e.getMessage());
        }
    }

    /**
     * ATUALIZAR FUNCIONÁRIO - Atualiza dados de um funcionário existente
     */
    public Funcionario atualizar(Long id, Funcionario funcionarioAtualizado) {
        return repository.findById(id)
                .map(funcionario -> {
                    // Atualiza apenas os campos permitidos
                    funcionario.setNome(funcionarioAtualizado.getNome());
                    funcionario.setEmail(funcionarioAtualizado.getEmail());
                    funcionario.setTelefone(funcionarioAtualizado.getTelefoneFormatado()); // ✅ Formata telefone
                    funcionario.setCargo(funcionarioAtualizado.getCargo());
                    funcionario.setSalario(funcionarioAtualizado.getSalario());

                    if (!funcionario.validarDados()) {
                        throw new IllegalArgumentException("Dados atualizados são inválidos");
                    }

                    return repository.save(funcionario);
                })
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado com ID: " + id));
    }
    /**
     * LISTAR TODOS OS FUNCIONÁRIOS
     */
    public List<Funcionario> listarTodos() {
        return repository.findAll();
    }

    /**
     * LISTAR FUNCIONÁRIOS ATIVOS
     */
    public List<Funcionario> listarAtivos() {
        return repository.findByAtivoTrue();
    }

    /**
     * BUSCAR FUNCIONÁRIO POR ID
     */
    public Optional<Funcionario> buscarPorId(Long id) {
        return repository.findById(id);
    }

    /**
     * DEMITIR FUNCIONÁRIO - Marca funcionário como inativo
     */
    public void demitirFuncionario(Long id) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        funcionario.demitir();
        repository.save(funcionario);
    }

    /**
     * REATIVAR FUNCIONÁRIO - Marca funcionário como ativo novamente
     */
    public void reativarFuncionario(Long id) {
        Funcionario funcionario = repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        funcionario.reativar();
        repository.save(funcionario);
    }

    /**
     * BUSCAR FUNCIONÁRIOS POR CARGO
     */
    public List<Funcionario> buscarPorCargo(String cargo) {
        return repository.findByCargo(cargo);
    }

    /**
     * BUSCAR FUNCIONÁRIOS POR NOME
     */
    public List<Funcionario> buscarPorNome(String nome) {
        return repository.findByNomeContainingIgnoreCase(nome);
    }
}