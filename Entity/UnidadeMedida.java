package org.example.entity;

// Enum auxiliar (UML: Classe auxiliar)
public enum UnidadeMedida {
    KG("Quilograma"),
    UN("Unidade"),
    LT("Litro"),
    ML("Mililitro"),
    GR("Grama"),
    CX("Caixa");

    private final String descricao;

    UnidadeMedida(String descricao) {
        this.descricao = descricao;
    }

    public String getDescricao() {
        return descricao;
    }
}
