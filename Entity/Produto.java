package org.example.entity;

import java.math.BigDecimal;

/**
 * ENTITY - Representa a entidade Produto do sistema
 * Contém apenas os dados e validações básicas da entidade
 * Não possui lógica de negócio complexa
 */
public class Produto {
    // Atributos da entidade (dados)
    private Integer id;
    private String nome;
    private int quantidade;
    private BigDecimal valor;
    private UnidadeMedida unidadeMedida;
    private String categoria;

    /**
     * Construtor da entidade Produto
     * @param id Identificador único
     * @param nome Nome do produto
     * @param quantidade Quantidade em estoque
     * @param valor Preço do produto
     * @param unidadeMedida Unidade de medida
     * @param categoria Categoria do produto
     */
    public Produto(Integer id, String nome, int quantidade, BigDecimal valor,
                   UnidadeMedida unidadeMedida, String categoria) {
        this.id = id;
        this.nome = nome;
        this.quantidade = quantidade;
        this.valor = valor;
        this.unidadeMedida = unidadeMedida;
        this.categoria = categoria;
    }

    /**
     * Método de validação básica dos dados da entidade
     * Verifica se os dados estão consistentes
     * @return true se os dados são válidos, false caso contrário
     */
    public boolean validarDados() {
        if (nome == null || nome.isBlank()) return false;
        if (quantidade < 0) return false;
        if (valor == null || valor.compareTo(BigDecimal.ZERO) <= 0) return false;
        if (unidadeMedida == null) return false;
        if (categoria == null || categoria.isBlank()) return false;
        return true;
    }

    /**
     * Método para exibir os dados do produto formatados
     * @return String formatada com todos os dados do produto
     */
    public String exibir() {
        return String.format(
                "📦 Produto ID: %d\n" +
                        "├── Nome: %s\n" +
                        "├── Quantidade: %d %s\n" +
                        "├── Valor: R$ %.2f\n" +
                        "└── Categoria: %s\n",
                id, nome, quantidade, unidadeMedida, valor, categoria
        );
    }

    // ============= GETTERS E SETTERS =============
    // Métodos de acesso aos dados da entidade

    public Integer getId() { return id; }
    public void setId(Integer id) { this.id = id; }

    public String getNome() { return nome; }
    public void setNome(String nome) { this.nome = nome; }

    public int getQuantidade() { return quantidade; }
    public void setQuantidade(int quantidade) { this.quantidade = quantidade; }

    public BigDecimal getValor() { return valor; }
    public void setValor(BigDecimal valor) { this.valor = valor; }

    public UnidadeMedida getUnidadeMedida() { return unidadeMedida; }
    public void setUnidadeMedida(UnidadeMedida unidadeMedida) { this.unidadeMedida = unidadeMedida; }

    public String getCategoria() { return categoria; }
    public void setCategoria(String categoria) { this.categoria = categoria; }
}

