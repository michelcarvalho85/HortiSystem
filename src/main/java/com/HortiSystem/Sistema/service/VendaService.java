package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.ItemVenda;
import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.model.Venda;
import com.HortiSystem.Sistema.repository.ProdutoRepository;
import com.HortiSystem.Sistema.repository.VendaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

/**
 * SERVIÇO DE VENDAS - Contém a lógica de negócio para operações de vendas
 * Gerencia todo o processo de venda, desde a criação até o controle de estoque
 */
@Service
public class VendaService {

    @Autowired
    private VendaRepository vendaRepository;

    @Autowired
    private ProdutoRepository produtoRepository;

    @Autowired
    private ProdutoService produtoService;

    /**
     * PROCESSAR VENDA - Método principal que processa uma venda completa
     * @Transactional garante que toda a operação seja atômica
     */
    @Transactional
    public Venda processarVenda(Venda venda) {
        // Valida a venda antes de processar
        if (!venda.validarVenda()) {
            throw new IllegalArgumentException("Venda inválida. Verifique os itens e dados.");
        }

        // Para cada item, verifica estoque e atualiza quantidade
        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();

            // Verifica se há estoque suficiente
            if (produto.getQuantidade() < item.getQuantidade()) {
                throw new IllegalArgumentException(
                        "Estoque insuficiente para o produto: " + produto.getNome() +
                                ". Disponível: " + produto.getQuantidade()
                );
            }

            // Atualiza estoque do produto
            produto.setQuantidade(produto.getQuantidade() - item.getQuantidade());
            produtoRepository.save(produto);
        }

        // Salva a venda no banco de dados
        return vendaRepository.save(venda);
    }

    /**
     * CANCELAR VENDA - Cancela uma venda e devolve os produtos ao estoque
     */
    @Transactional
    public void cancelarVenda(Long vendaId) {
        Venda venda = vendaRepository.findById(vendaId)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));

        // Devolve cada item ao estoque
        for (ItemVenda item : venda.getItens()) {
            Produto produto = item.getProduto();
            produto.setQuantidade(produto.getQuantidade() + item.getQuantidade());
            produtoRepository.save(produto);
        }

        // Marca venda como cancelada
        venda.cancelar();
        vendaRepository.save(venda);
    }

    /**
     * BUSCAR VENDA POR ID
     */
    public Venda buscarPorId(Long id) {
        return vendaRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Venda não encontrada"));
    }

    /**
     * LISTAR TODAS AS VENDAS
     */
    public List<Venda> listarTodas() {
        return vendaRepository.findAll();
    }

    /**
     * LISTAR VENDAS POR PERÍODO
     */
    public List<Venda> listarPorPeriodo(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);
        return vendaRepository.findByDataVendaBetween(inicio, fim);
    }

    /**
     * CALCULAR TOTAL DE VENDAS DO DIA
     */
    public BigDecimal calcularTotalVendasHoje() {
        LocalDateTime inicio = LocalDate.now().atStartOfDay();
        LocalDateTime fim = LocalDateTime.now();
        BigDecimal total = vendaRepository.calcularTotalVendasPeriodo(inicio, fim);
        return total != null ? total : BigDecimal.ZERO;
    }

    /**
     * CRIAR VENDA RÁPIDA - Método auxiliar para criar venda com itens
     */
    public Venda criarVendaRapida(List<ItemVenda> itens, String formaPagamento, String nomeCliente) {
        Venda venda = new Venda();
        venda.setFormaPagamento(formaPagamento);
        venda.setNomeCliente(nomeCliente);

        for (ItemVenda item : itens) {
            venda.adicionarItem(item);
        }

        return venda;
    }

    /**
     * GERAR RELATÓRIO DE VENDAS - Retorna estatísticas de vendas
     */
    public RelatorioVendas gerarRelatorioVendas(LocalDate dataInicio, LocalDate dataFim) {
        LocalDateTime inicio = dataInicio.atStartOfDay();
        LocalDateTime fim = dataFim.atTime(23, 59, 59);

        BigDecimal totalVendas = vendaRepository.calcularTotalVendasPeriodo(inicio, fim);
        Long quantidadeVendas = vendaRepository.contarVendasPeriodo(inicio, fim);
        List<Venda> vendas = vendaRepository.findByDataVendaBetween(inicio, fim);

        return new RelatorioVendas(totalVendas != null ? totalVendas : BigDecimal.ZERO,
                quantidadeVendas != null ? quantidadeVendas : 0L,
                vendas);
    }

    // Classe interna para o relatório
    public static class RelatorioVendas {
        private final BigDecimal totalVendas;
        private final Long quantidadeVendas;
        private final List<Venda> vendas;

        public RelatorioVendas(BigDecimal totalVendas, Long quantidadeVendas, List<Venda> vendas) {
            this.totalVendas = totalVendas;
            this.quantidadeVendas = quantidadeVendas;
            this.vendas = vendas;
        }

        // Getters
        public BigDecimal getTotalVendas() { return totalVendas; }
        public Long getQuantidadeVendas() { return quantidadeVendas; }
        public List<Venda> getVendas() { return vendas; }
    }
}