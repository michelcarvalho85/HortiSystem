package com.HortiSystem.Sistema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDate;

/**
 * ENTIDADE FUNCIONÁRIO - Representa os funcionários do sistema HortiSystem
 * Mapeia para a tabela 'funcionario_table' no banco de dados
 */
@Entity
@Table(name = "funcionario_table")
@Getter
@Setter
public class Funcionario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false)
    private String nome;

    @Column(nullable = false, unique = true)
    private String cpf;

    @Column(nullable = false)
    private String email;

    @Column(nullable = false)
    private String telefone;

    @Column(nullable = false)
    private String cargo; // GERENTE, VENDEDOR, ESTOQUISTA, CAIXA

    @Column(nullable = false)
    private BigDecimal salario;

    @Column(nullable = false)
    private LocalDate dataAdmissao;

    private LocalDate dataDemissao;

    @Column(nullable = false)
    private boolean ativo = true;

    // CONSTRUTORES
    public Funcionario() {}

    public Funcionario(String nome, String cpf, String email, String telefone,
                       String cargo, BigDecimal salario, LocalDate dataAdmissao) {
        this.nome = nome;
        this.cpf = cpf;
        this.email = email;
        this.telefone = telefone;
        this.cargo = cargo;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
        this.ativo = true;
    }

    /**
     * MÉTODO DE VALIDAÇÃO - Verifica se os dados do funcionário são válidos
     * ✅ CORRIGIDO: Validação mais flexível para desenvolvimento
     */
    public boolean validarDados() {
        // ✅ Validação do Nome
        if (nome == null || nome.isBlank() || nome.trim().length() < 2) {
            System.out.println("❌ Nome inválido: " + nome);
            return false;
        }

        // ✅ Validação do CPF (aceita com ou sem formatação)
        if (cpf == null || cpf.isBlank()) {
            System.out.println("❌ CPF vazio");
            return false;
        }

        // Remove formatação do CPF para validar
        String cpfLimpo = cpf.replaceAll("[^0-9]", "");
        if (cpfLimpo.length() != 11) {
            System.out.println("❌ CPF inválido (deve ter 11 dígitos): " + cpf);
            return false;
        }

        // ✅ Validação do Email (mais flexível)
        if (email == null || email.isBlank() || !email.contains("@")) {
            System.out.println("❌ Email inválido: " + email);
            return false;
        }

        // ✅ Validação do Telefone (aceita formatado)
        if (telefone == null || telefone.isBlank()) {
            System.out.println("❌ Telefone vazio");
            return false;
        }

        // Remove formatação do telefone para validar
        String telefoneLimpo = telefone.replaceAll("[^0-9]", "");
        if (telefoneLimpo.length() < 10) {
            System.out.println("❌ Telefone inválido (mínimo 10 dígitos): " + telefone);
            return false;
        }

        // ✅ Validação do Cargo
        if (cargo == null || cargo.isBlank()) {
            System.out.println("❌ Cargo vazio");
            return false;
        }

        // ✅ Validação do Salário
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) {
            System.out.println("❌ Salário inválido: " + salario);
            return false;
        }

        // ✅ Validação da Data de Admissão
        if (dataAdmissao == null) {
            System.out.println("❌ Data de admissão vazia");
            return false;
        }

        // ✅ Permite data futura para testes (remova em produção)
        if (dataAdmissao.isAfter(LocalDate.now().plusDays(30))) {
            System.out.println("⚠️ Data de admissão no futuro: " + dataAdmissao);
            // Não retorna false, apenas avisa (para testes)
        }

        System.out.println("✅ Dados válidos para funcionário: " + nome);
        return true;
    }

    /**
     * MÉTODO PARA FORMATAR CPF (remove formatação para salvar no banco)
     */
    public String getCpfFormatado() {
        if (cpf == null) return "";
        return cpf.replaceAll("[^0-9]", "");
    }

    /**
     * MÉTODO PARA FORMATAR TELEFONE (remove formatação para salvar no banco)
     */
    public String getTelefoneFormatado() {
        if (telefone == null) return "";
        return telefone.replaceAll("[^0-9]", "");
    }

    /**
     * MÉTODO PARA DEMITIR FUNCIONÁRIO
     */
    public void demitir() {
        this.ativo = false;
        this.dataDemissao = LocalDate.now();
    }

    /**
     * MÉTODO PARA REATIVAR FUNCIONÁRIO
     */
    public void reativar() {
        this.ativo = true;
        this.dataDemissao = null;
    }
}