package com.HortiSystem.Sistema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * ENTIDADE VENDA - Representa uma venda no sistema com nota fiscal
 * Sistema estilo caixa de supermercado com código de produtos
 */
@Entity
@Table(name = "venda_table")
@Getter
@Setter
public class Venda {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(nullable = false, unique = true)
    private String numeroNotaFiscal; // Número único da nota fiscal

    @Column(nullable = false)
    private LocalDateTime dataVenda = LocalDateTime.now();

    @Column(nullable = false)
    private BigDecimal valorTotal = BigDecimal.ZERO;

    @Column(nullable = false)
    private BigDecimal valorRecebido = BigDecimal.ZERO; // Valor recebido do cliente

    @Column(nullable = false)
    private BigDecimal troco = BigDecimal.ZERO; // Troco para o cliente

    // Informações do cliente
    private String nomeCliente = "Consumidor"; // Padrão para cliente não identificado
    private String cpfCliente;
    private String telefoneCliente;

    @Column(nullable = false)
    private String formaPagamento; // DINHEIRO, CARTAO_CREDITO, CARTAO_DEBITO, PIX

    @Column(nullable = false)
    private String status = "FINALIZADA"; // FINALIZADA, CANCELADA

    // Relacionamento com itens da venda
    @OneToMany(mappedBy = "venda", cascade = CascadeType.ALL, orphanRemoval = true)
    private List<ItemVenda> itens = new ArrayList<>();

    /**
     * CONSTRUTOR - Gera número da nota fiscal automaticamente
     */
    public Venda() {
        this.numeroNotaFiscal = gerarNumeroNotaFiscal();
    }

    /**
     * GERAR NÚMERO DA NOTA FISCAL - Formato: NF202410160001 (ANO MÊS DIA SEQUENCIA)
     */
    private String gerarNumeroNotaFiscal() {
        String data = LocalDateTime.now().format(java.time.format.DateTimeFormatter.ofPattern("yyyyMMdd"));
        // Na prática, você buscaria a última sequência do banco
        String sequencia = String.format("%04d", (int)(Math.random() * 1000) + 1);
        return "NF" + data + sequencia;
    }

    /**
     * CALCULAR VALOR TOTAL - Soma o valor de todos os itens da venda
     */
    public void calcularValorTotal() {
        this.valorTotal = itens.stream()
                .map(ItemVenda::getSubtotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);

        // Calcula troco automaticamente
        calcularTroco();
    }

    /**
     * CALCULAR TROCO - Valor Recebido - Valor Total
     */
    public void calcularTroco() {
        if (valorRecebido != null && valorTotal != null) {
            this.troco = valorRecebido.subtract(valorTotal);
            if (this.troco.compareTo(BigDecimal.ZERO) < 0) {
                this.troco = BigDecimal.ZERO; // Não permite troco negativo
            }
        }
    }

    /**
     * ADICIONAR ITEM - Adiciona um item à venda e atualiza o total
     */
    public void adicionarItem(ItemVenda item) {
        item.setVenda(this);
        this.itens.add(item);
        calcularValorTotal();
    }

    /**
     * REMOVER ITEM - Remove um item da venda e atualiza o total
     */
    public void removerItem(ItemVenda item) {
        this.itens.remove(item);
        item.setVenda(null);
        calcularValorTotal();
    }

    /**
     * VALIDAR VENDA - Verifica se a venda tem dados mínimos válidos
     */
    public boolean validarVenda() {
        if (itens == null || itens.isEmpty()) {
            return false;
        }
        if (formaPagamento == null || formaPagamento.isBlank()) {
            return false;
        }
        if (valorTotal.compareTo(BigDecimal.ZERO) <= 0) {
            return false;
        }
        // Para pagamento em dinheiro, verifica se valor recebido é suficiente
        if ("DINHEIRO".equals(formaPagamento) &&
                valorRecebido.compareTo(valorTotal) < 0) {
            return false;
        }
        return true;
    }

    /**
     * CANCELAR VENDA - Marca a venda como cancelada
     */
    public void cancelar() {
        this.status = "CANCELADA";
    }

    /**
     * OBTER QUANTIDADE TOTAL DE ITENS
     */
    public int getQuantidadeTotalItens() {
        return itens.stream()
                .mapToInt(ItemVenda::getQuantidade)
                .sum();
    }
}