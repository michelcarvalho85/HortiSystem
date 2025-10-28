package com.HortiSystem.Sistema.controller;

import com.HortiSystem.Sistema.model.ItemVenda;
import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.model.Venda;
import com.HortiSystem.Sistema.service.ProdutoService;
import com.HortiSystem.Sistema.service.VendaService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

/**
 * CONTROLADOR DE VENDAS - Sistema de caixa estilo supermercado
 * Com código do produto e geração de nota fiscal
 */
@Controller
@RequestMapping("/caixa")
public class VendaController {

    @Autowired
    private VendaService vendaService;

    @Autowired
    private ProdutoService produtoService;

    /**
     * PÁGINA PRINCIPAL DO CAIXA - GET /caixa
     * Interface estilo supermercado com código do produto
     */
    @GetMapping
    public String exibirCaixa(Model model) {
        // Inicializa uma nova venda
        Venda venda = new Venda();
        model.addAttribute("venda", venda);
        model.addAttribute("itensVenda", new ArrayList<ItemVenda>());
        model.addAttribute("produtos", produtoService.listarTodos()); // Para busca rápida

        return "caixa"; // templates/caixa.html
    }

    /**
     * ADICIONAR ITEM POR CÓDIGO - POST /caixa/adicionar
     * Simula a leitura de código de barras (usa ID do produto como código)
     */
    @PostMapping("/adicionar")
    public String adicionarItem(@RequestParam String codigoProduto,
                                @RequestParam Integer quantidade,
                                RedirectAttributes redirectAttributes) {
        try {
            // Converte o código para Long (ID do produto)
            Long produtoId = Long.parseLong(codigoProduto);

            // Busca o produto pelo ID (simulando código de barras)
            Optional<Produto> produtoOpt = produtoService.listarTodos().stream()
                    .filter(p -> p.getId().equals(produtoId))
                    .findFirst();

            if (produtoOpt.isEmpty()) {
                redirectAttributes.addFlashAttribute("error", "Produto não encontrado! Código: " + codigoProduto);
                return "redirect:/caixa";
            }

            Produto produto = produtoOpt.get();

            // Verifica estoque
            if (produto.getQuantidade() < quantidade) {
                redirectAttributes.addFlashAttribute("error",
                        "Estoque insuficiente! Disponível: " + produto.getQuantidade());
                return "redirect:/caixa";
            }

            // Cria o item da venda
            ItemVenda item = new ItemVenda(produto, quantidade);

            // Adiciona à venda na sessão (em produção, use sessão HTTP)
            redirectAttributes.addFlashAttribute("novoItem", item);
            redirectAttributes.addFlashAttribute("success",
                    "Produto adicionado: " + produto.getNome() + " - R$ " + produto.getValor());

        } catch (NumberFormatException e) {
            redirectAttributes.addFlashAttribute("error", "Código inválido! Use apenas números.");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao adicionar produto: " + e.getMessage());
        }

        return "redirect:/caixa";
    }

    /**
     * FINALIZAR VENDA - POST /caixa/finalizar
     * Processa o pagamento e gera a nota fiscal
     */
    @PostMapping("/finalizar")
    public String finalizarVenda(@ModelAttribute Venda venda,
                                 RedirectAttributes redirectAttributes) {
        try {
            // Para simulação, criamos uma venda com itens de exemplo
            // Em produção, os itens viriam da sessão
            Venda vendaProcessada = processarVendaComItens(venda);

            redirectAttributes.addFlashAttribute("vendaFinalizada", vendaProcessada);
            redirectAttributes.addFlashAttribute("success",
                    "Venda finalizada com sucesso! Nº: " + vendaProcessada.getNumeroNotaFiscal());

            return "redirect:/caixa/nota-fiscal/" + vendaProcessada.getId();

        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao finalizar venda: " + e.getMessage());
            return "redirect:/caixa";
        }
    }

    /**
     * EXIBIR NOTA FISCAL - GET /caixa/nota-fiscal/{id}
     */
    @GetMapping("/nota-fiscal/{id}")
    public String exibirNotaFiscal(@PathVariable Long id, Model model) {
        try {
            Venda venda = vendaService.buscarPorId(id);
            model.addAttribute("venda", venda);
            return "nota-fiscal"; // templates/nota-fiscal.html
        } catch (Exception e) {
            model.addAttribute("error", "Nota fiscal não encontrada: " + e.getMessage());
            return "redirect:/caixa";
        }
    }

    /**
     * CANCELAR VENDA - POST /caixa/cancelar
     */
    @PostMapping("/cancelar")
    public String cancelarVenda(RedirectAttributes redirectAttributes) {
        redirectAttributes.addFlashAttribute("info", "Venda cancelada. Nova venda iniciada.");
        return "redirect:/caixa";
    }

    /**
     * MÉTODO AUXILIAR - Processa venda com itens de exemplo
     * Em produção, os itens viriam da sessão do usuário
     */
    private Venda processarVendaComItens(Venda venda) {
        // Adiciona alguns itens de exemplo para demonstração
        // Em produção, isso viria do carrinho da sessão
        List<Produto> produtos = produtoService.listarTodos();

        if (!produtos.isEmpty()) {
            // Adiciona os dois primeiros produtos como exemplo
            for (int i = 0; i < Math.min(2, produtos.size()); i++) {
                Produto produto = produtos.get(i);
                ItemVenda item = new ItemVenda(produto, 1); // 1 unidade de cada
                venda.adicionarItem(item);
            }
        }

        // Processa a venda no sistema
        return vendaService.processarVenda(venda);
    }

    /**
     * BUSCAR PRODUTO POR CÓDIGO - API JSON
     */
    @GetMapping("/api/produto/{codigo}")
    @ResponseBody
    public Produto buscarProdutoPorCodigo(@PathVariable String codigo) {
        try {
            Long produtoId = Long.parseLong(codigo);
            return produtoService.listarTodos().stream()
                    .filter(p -> p.getId().equals(produtoId))
                    .findFirst()
                    .orElseThrow(() -> new RuntimeException("Produto não encontrado"));
        } catch (NumberFormatException e) {
            throw new RuntimeException("Código inválido");
        }
    }
}