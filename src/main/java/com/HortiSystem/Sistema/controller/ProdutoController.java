package com.HortiSystem.Sistema.controller;

import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.service.ProdutoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import java.util.List;

/**
 * CONTROLADOR DE PRODUTOS - Gerencia as requisições HTTP relacionadas a produtos
 * Esta classe atua como intermediária entre a view (frontend) e o service (regras de negócio)
 */
@Controller
@RequestMapping("/produto") // Define que todas as URLs começam com /produto
public class ProdutoController {

    @Autowired
    private ProdutoService service;
    private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

    /**
     * 1) Mostrar formulário de cadastro (GET /produto/novo)
     */
    @GetMapping("/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastro-produto"; // templates/cadastro-produto.html
    }

    /**
     * 2) Processar cadastro (POST /produto) - ✅ CORRIGIDO COM REDIRECIONAMENTO
     */
    @PostMapping
    public String cadastrar(@ModelAttribute("produto") Produto produto,
                            RedirectAttributes redirectAttributes) { // ✅ Usa RedirectAttributes
        try {
            if (produto.ValidadarDados()) {
                service.salvar(produto);
                redirectAttributes.addFlashAttribute("success", "Produto cadastrado com sucesso!");
                return "redirect:/produto/novo"; // ✅ Redireciona para evitar reenvio
            } else {
                redirectAttributes.addFlashAttribute("error", "Dados inválidos! Verifique os campos.");
                return "redirect:/produto/novo";
            }
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar: " + e.getMessage());
            return "redirect:/produto/novo";
        }
    }

    /**
     * 3) Exibir página de lista de produtos (GET /produto/lista)
     */
    @GetMapping("/lista")
    public String mostrarLista(Model model) {
        List<Produto> produtos = service.listarTodos();
        model.addAttribute("produtos", produtos);
        return "lista-produtos"; // templates/lista-produtos.html
    }

    /**
     * 4) API JSON para buscar todos os produtos (GET /produto/todos)
     */
    @GetMapping("/todos")
    @ResponseBody
    public List<Produto> listarTodosProdutos() {
        return service.listarTodos();
    }
}