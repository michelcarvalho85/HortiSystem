package com.HortiSystem.Sistema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

/**
 * ENTIDADE ITEM VENDA - Representa um item individual dentro de uma venda
 * Sistema com código do produto (simulando código de barras)
 */
@Entity
@Table(name = "item_venda_table")
@Getter
@Setter
public class ItemVenda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "venda_id", nullable = false)
    private Venda venda;

    @ManyToOne
    @JoinColumn(name = "produto_id", nullable = false)
    private Produto produto;

    @Column(nullable = false)
    private Integer quantidade;

    @Column(nullable = false)
    private BigDecimal valorUnitario;

    @Column(nullable = false)
    private BigDecimal subtotal;

    /**
     * CALCULAR SUBTOTAL - Quantidade × Valor Unitário
     */
    public void calcularSubtotal() {
        if (quantidade != null && valorUnitario != null) {
            this.subtotal = valorUnitario.multiply(BigDecimal.valueOf(quantidade));
        }
    }

    /**
     * CONSTRUTOR PARA FACILITAR CRIAÇÃO
     */
    public ItemVenda(Produto produto, Integer quantidade) {
        this.produto = produto;
        this.quantidade = quantidade;
        this.valorUnitario = produto.getValor();
        calcularSubtotal();
    }

    public ItemVenda() {}

    /**
     * OBTER DESCRIÇÃO DO ITEM PARA NOTA FISCAL
     */
    public String getDescricaoNota() {
        return String.format("%s - %d x R$ %.2f",
                produto.getNome(), quantidade, valorUnitario);
    }
}