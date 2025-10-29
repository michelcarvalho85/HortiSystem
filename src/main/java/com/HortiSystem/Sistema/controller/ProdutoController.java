package com.HortiSystem.Sistema.controller;

import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.service.LGPDLogDbService;
import com.HortiSystem.Sistema.service.LGPDLogService;
import com.HortiSystem.Sistema.service.ProdutoService;
import com.HortiSystem.Sistema.service.LGPDLogDbService; // vamos usar para registrar logs
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.Principal;
import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/produto")
public class ProdutoController {

    @Autowired
    private ProdutoService service;

    @Autowired
    private LGPDLogDbService lgpdLogDbService;

    @Autowired
    private LGPDLogService lgpdLogService;

    private static final Logger log = LoggerFactory.getLogger(ProdutoController.class);

    @GetMapping("/novo")
    public String novoProdutoForm(Model model) {
        model.addAttribute("produto", new Produto());
        return "cadastro-produto";
    }

    @PostMapping("/salvar")
    public String cadastrar(@ModelAttribute("produto") Produto produto,
                            RedirectAttributes redirectAttributes,
                            Principal principal,
                            HttpServletRequest request) {
        try {
            if (produto.validarDados()) {
                Produto salvo = service.salvar(produto);

                // info do usuário/cliente
                String usuario = principal != null ? principal.getName() : "ANON";
                Long usuarioId = null; // preencha se tiver como obter o id do usuário
                String clientIp = request != null ? request.getRemoteAddr() : null;

                // --- gravar no banco (assinatura completa: 8 args ou 7 args dependendo da sua impl) ---
                // Use registrarEvento (8 parâmetros) — é a forma mais explícita:
                lgpdLogDbService.registrarEvento(
                        usuario,                                     // usuario
                        usuarioId,                                   // usuarioId (nullable)
                        "PRODUCT.CREATE",                             // acao
                        "PRODUTO",                                    // recurso
                        "id=" + salvo.getId() + " nome=" + salvo.getNome(), // detalhes
                        clientIp,                                     // ip
                        "INFO",                                       // nivel
                        null                                          // correlationId (nullable)
                );

                // --- gravar em arquivo (seu serviço de arquivo usa 3 parâmetros) ---
                lgpdLogService.registrarEvento(
                        usuario,
                        "PRODUCT.CREATE",
                        "Produto id=" + salvo.getId() + " nome=" + salvo.getNome()
                );

                redirectAttributes.addFlashAttribute("success", "Produto cadastrado com sucesso!");
                return "redirect:/produto/lista";
            } else {
                redirectAttributes.addFlashAttribute("error", "Dados inválidos! Verifique os campos.");
                return "redirect:/produto/novo";
            }
        } catch (Exception e) {
            log.error("Erro ao salvar produto", e);
            redirectAttributes.addFlashAttribute("error", "Erro ao salvar: " + e.getMessage());
            return "redirect:/produto/novo";
        }
    }

    // Mapeamento correto: /produto/editar/{id}
    @GetMapping("/editar/{id}")
    public String editarProdutoForm(@PathVariable Long id, Model model, RedirectAttributes ra) {
        Optional<Produto> produtoOpt = service.buscarPorId(id);
        if (produtoOpt.isEmpty()) {
            ra.addFlashAttribute("error", "Produto não encontrado!");
            return "redirect:/produto/lista";
        }
        model.addAttribute("produto", produtoOpt.get());
        return "editar-produto";
    }

    // método corrigido
    @PostMapping("/editar")
    public String atualizarProduto(@ModelAttribute("produto") Produto produto,
                                   RedirectAttributes redirectAttributes,
                                   Principal principal,
                                   HttpServletRequest request) { // <- para pegar IP
        try {
            if (produto.validarDados()) {
                Produto atualizado = service.salvar(produto);

                String usuario = principal != null ? principal.getName() : "ANON";
                Long usuarioId = null; // preencha se você tiver o id do usuário no contexto
                String clientIp = request != null ? request.getRemoteAddr() : "0.0.0.0";

                // Chamada ao serviço de DB usando a assinatura completa (8 args)
                lgpdLogDbService.registrarEvento(
                        usuario,                                 // usuario
                        usuarioId,                               // usuarioId (nullable)
                        "PRODUCT.UPDATE",                         // acao
                        "PRODUTO",                                // recurso
                        "id=" + atualizado.getId() + " nome=" + atualizado.getNome(), // detalhes
                        clientIp,                                 // ip
                        "INFO",                                    // nivel
                        null                                       // correlationId (nullable)
                );

                // Também registrar em arquivo (opcional)
                lgpdLogService.registrarEvento(
                        usuario,
                        "PRODUCT.UPDATE",
                        "Produto id=" + atualizado.getId() + " nome=" + atualizado.getNome()
                );

                redirectAttributes.addFlashAttribute("success", "Produto atualizado com sucesso!");
                return "redirect:/produto/lista";
            } else {
                redirectAttributes.addFlashAttribute("error", "Dados inválidos. Verifique os campos.");
                return "redirect:/produto/editar/" + produto.getId();
            }
        } catch (Exception e) {
            log.error("Erro ao atualizar produto", e);
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar: " + e.getMessage());
            return "redirect:/produto/editar/" + produto.getId();
        }
    }

    @PostMapping("/deletar/{id}")
    public String deletarProduto(@PathVariable Long id,
                                 RedirectAttributes ra,
                                 Principal principal,
                                 HttpServletRequest request) {
        try {
            // executa a remoção
            service.deletar(id);

            // informações do usuário/cliente
            String usuario = principal != null ? principal.getName() : "ANON";
            Long usuarioId = null; // preencha se souber o id do usuário
            String clientIp = request != null ? request.getRemoteAddr() : null;

            // --- registra no banco (assinatura completa) ---
            lgpdLogDbService.registrarEvento(
                    usuario,                    // usuario
                    usuarioId,                  // usuarioId (nullable)
                    "PRODUCT.DELETE",           // acao
                    "PRODUTO",                  // recurso
                    "id=" + id,                 // detalhes
                    clientIp,                   // ip
                    "INFO",                     // nivel
                    null                        // correlationId (nullable)
            );

            // --- registra também em arquivo (opcional) ---
            lgpdLogService.registrarEvento(
                    usuario,
                    "PRODUCT.DELETE",
                    "Produto id=" + id
            );

            ra.addFlashAttribute("success", "Produto removido.");
        } catch (Exception e) {
            log.error("Erro ao deletar produto", e);
            ra.addFlashAttribute("error", "Erro ao remover produto: " + e.getMessage());
        }
        return "redirect:/produto/lista";
    }


    @GetMapping("/lista")
    public String mostrarLista(Model model) {
        List<Produto> produtos = service.listarTodos();
        model.addAttribute("produtos", produtos);
        return "lista-produtos";
    }

    @GetMapping("/todos")
    @ResponseBody
    public List<Produto> listarTodosProdutos() {
        return service.listarTodos();
    }
}
