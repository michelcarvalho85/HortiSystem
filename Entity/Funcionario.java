package org.example.entity;

import java.math.BigDecimal;
import java.time.LocalDate;

public class Funcionario {

    private Integer idFuncionario;
    private String nome;
    private String cpf;
    private Cargo cargo;
    private BigDecimal salario;
    private LocalDate dataAdmissao;
    private boolean ativo;
    private String login;
    private String senha;

    public Funcionario(Integer idFuncionario , String nome , String cpf , Cargo cargo , BigDecimal salario ,
                       LocalDate dataAdmissao , boolean ativo , String login , String senha){

        this.idFuncionario = idFuncionario;
        this.nome = nome;
        this.cpf = cpf;
        this.cargo = cargo;
        this.salario = salario;
        this.dataAdmissao = dataAdmissao;
        this.ativo = ativo;
        this.login = login;
        this.senha = senha;

    }

    /**
     * Validação básica dos dados do funcionário
     */
    public boolean validarDados() {
        if (nome == null || nome.isBlank()) return false;
        if (cpf == null || cpf.length() != 11) return false;
        if (cargo == null) return false;
        if (salario == null || salario.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (dataAdmissao == null || dataAdmissao.isAfter(LocalDate.now())) return false;
        if (login == null || login.isBlank()) return false;
        if (senha == null || senha.length() < 6) return false;
        return true;
    }

    /**
     * Método para autenticar o funcionário
     */
    public boolean autenticar(String login, String senha) {
        return this.ativo && this.login.equals(login) && this.senha.equals(senha);
    }

    /**
     * Método para exibir dados do funcionário (sem senha)
     */
    public String exibir() {
        return String.format(
                "👤 Funcionário ID: %d\n" +
                        "├── Nome: %s\n" +
                        "├── CPF: %s\n" +
                        "├── Cargo: %s\n" +
                        "├── Salário: R$ %.2f\n" +
                        "├── Admissão: %s\n" +
                        "├── Login: %s\n" +
                        "└── Status: %s\n",
                idFuncionario, nome, formatarCpf(cpf), cargo,
                salario, dataAdmissao, login, ativo ? "Ativo" : "Inativo"
        );
    }

    private String formatarCpf(String cpf) {
        return cpf.replaceAll("(\\d{3})(\\d{3})(\\d{3})(\\d{2})", "$1.$2.$3-$4");
    }

    // GETTERS e SETTERS
    public Integer getIdFuncionario() { return idFuncionario; }
    public void setIdFuncionario(Integer idFuncionario) { this.idFuncionario = idFuncionario; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public String getCpf() { return cpf; }
    public void setCpf(String cpf) { this.cpf = cpf; }

    public Cargo getCargo() { return cargo; }
    public void setCargo(Cargo cargo) { this.cargo = cargo; }

    public BigDecimal getSalario() { return salario; }
    public void setSalario(BigDecimal salario) { this.salario = salario; }

    public LocalDate getDataAdmissao() { return dataAdmissao; }
    public void setDataAdmissao(LocalDate dataAdmissao) { this.dataAdmissao = dataAdmissao; }

    public boolean isAtivo() { return ativo; }
    public void setAtivo(boolean ativo) { this.ativo = ativo; }

    public String getLogin() { return login; }
    public void setLogin(String login) { this.login = login; }

    public String getSenha() { return senha; }
    public void setSenha(String senha) { this.senha = senha; }
}


