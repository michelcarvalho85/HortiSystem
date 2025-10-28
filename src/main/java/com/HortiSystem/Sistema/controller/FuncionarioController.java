package com.HortiSystem.Sistema.controller;

import com.HortiSystem.Sistema.model.Funcionario;
import com.HortiSystem.Sistema.service.FuncionarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;
import java.util.List;

/**
 * CONTROLADOR DE FUNCIONÁRIOS - Gerencia as requisições HTTP relacionadas a funcionários
 * Responsável pela interface entre o usuário e o sistema de gestão de funcionários
 */
@Controller
@RequestMapping("/funcionarios") // Todas as URLs começam com /funcionarios
public class FuncionarioController {

    @Autowired
    private FuncionarioService funcionarioService;

    /**
     * EXIBIR FORMULÁRIO DE CADASTRO - GET /funcionarios/novo
     */
    @GetMapping("/novo")
    public String exibirFormularioCadastro(Model model) {
        model.addAttribute("funcionario", new Funcionario());
        return "cadastro-funcionario"; // templates/cadastro-funcionario.html
    }

    /**
     * PROCESSAR CADASTRO - POST /funcionarios - ✅ COM REDIRECIONAMENTO
     */
    @PostMapping
    public String cadastrarFuncionario(@ModelAttribute Funcionario funcionario,
                                       RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.salvar(funcionario);
            redirectAttributes.addFlashAttribute("success", "Funcionário cadastrado com sucesso!");
            return "redirect:/funcionarios/novo"; // ✅ Redireciona para evitar reenvio
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao cadastrar funcionário: " + e.getMessage());
            return "redirect:/funcionarios/novo";
        }
    }

    /**
     * EXIBIR LISTA DE FUNCIONÁRIOS - GET /funcionarios/lista
     */
    @GetMapping("/lista")
    public String exibirListaFuncionarios(Model model) {
        List<Funcionario> funcionarios = funcionarioService.listarAtivos();
        model.addAttribute("funcionarios", funcionarios);
        return "lista-funcionarios"; // templates/lista-funcionarios.html
    }

    /**
     * EXIBIR FORMULÁRIO DE EDIÇÃO - GET /funcionarios/editar/{id}
     */
    @GetMapping("/editar/{id}")
    public String exibirFormularioEdicao(@PathVariable Long id, Model model) {
        Funcionario funcionario = funcionarioService.buscarPorId(id)
                .orElseThrow(() -> new RuntimeException("Funcionário não encontrado"));
        model.addAttribute("funcionario", funcionario);
        return "editar-funcionario"; // templates/editar-funcionario.html
    }

    /**
     * PROCESSAR EDIÇÃO - POST /funcionarios/editar/{id} - ✅ COM REDIRECIONAMENTO
     */
    @PostMapping("/editar/{id}")
    public String editarFuncionario(@PathVariable Long id, @ModelAttribute Funcionario funcionario,
                                    RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.atualizar(id, funcionario);
            redirectAttributes.addFlashAttribute("success", "Funcionário atualizado com sucesso!");
            return "redirect:/funcionarios/lista"; // ✅ Redireciona para a lista
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao atualizar funcionário: " + e.getMessage());
            return "redirect:/funcionarios/editar/" + id;
        }
    }

    /**
     * DEMITIR FUNCIONÁRIO - POST /funcionarios/demitir/{id} - ✅ COM REDIRECIONAMENTO
     * Demite um funcionário do sistema (soft delete).
     * Ao invés de remover do banco, marca como inativo para manter histórico.
     */
    @PostMapping("/demitir/{id}")
    public String demitirFuncionario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.demitirFuncionario(id);
            redirectAttributes.addFlashAttribute("success", "Funcionário demitido com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao demitir funcionário: " + e.getMessage());
        }
        return "redirect:/funcionarios/lista"; // ✅ Redireciona para lista após demissão
    }

    /**
     * REATIVAR FUNCIONÁRIO - POST /funcionarios/reativar/{id} - ✅ COM REDIRECIONAMENTO
     */
    @PostMapping("/reativar/{id}")
    public String reativarFuncionario(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        try {
            funcionarioService.reativarFuncionario(id);
            redirectAttributes.addFlashAttribute("success", "Funcionário reativado com sucesso!");
        } catch (Exception e) {
            redirectAttributes.addFlashAttribute("error", "Erro ao reativar funcionário: " + e.getMessage());
        }
        return "redirect:/funcionarios/lista"; // ✅ Redireciona para lista após reativação
    }

    /**
     * API JSON - GET /funcionarios/api/todos
     */
    @GetMapping("/api/todos")
    @ResponseBody
    public List<Funcionario> listarTodosFuncionarios() {
        return funcionarioService.listarTodos();
    }

    /**
     * API JSON - GET /funcionarios/api/ativos
     */
    @GetMapping("/api/ativos")
    @ResponseBody
    public List<Funcionario> listarFuncionariosAtivos() {
        return funcionarioService.listarAtivos();
    }
}