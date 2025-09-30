package org.example.entity;

/**
 * ENUM - Representa os cargos dos funcionários
 */
public enum Cargo {
    GERENTE("Gerente"),
    VENDEDOR("Vendedor"),
    CAIXA("Caixa"),
    ESTOQUISTA("Estoquista"),
    ADMINISTRATIVO("Administrativo"),
    SUPORTE("Suporte Técnico");

    private final String descricao;

    Cargo(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }

    @Override
    public String toString() {
        return descricao;
    }
}
