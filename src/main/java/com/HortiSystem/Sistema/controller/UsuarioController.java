package com.HortiSystem.Sistema.controller;

import com.HortiSystem.Sistema.model.Usuario;
import com.HortiSystem.Sistema.service.LGPDLogService;
import com.HortiSystem.Sistema.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;

import jakarta.validation.Valid;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.ModelAttribute;
import jakarta.servlet.http.HttpSession;
import java.time.LocalDateTime;

@Controller
@RequestMapping("/usuario")
public class UsuarioController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LGPDLogService lgpdLogService;

    // form de cadastro (se usar)
    @GetMapping("/novo")
    public String novoForm(Model model) {
        model.addAttribute("usuario", new Usuario());
        return "cadastro-usuario";
    }

    @GetMapping("/consentimento")
    public String mostrarConsentimento(Model model) {
        Authentication auth = SecurityContextHolder.getContext().getAuthentication();
        if (auth == null || auth.getName() == null || auth.getName().equals("anonymousUser")) {
            return "redirect:/login";
        }
        Usuario usuario = usuarioService.buscarPorUsername(auth.getName());
        if (usuario == null) {
            return "redirect:/login";
        }
        model.addAttribute("usuario", usuario);
        return "consentimento-lgpd";
    }

    // POST que recebe o aceite do checkbox
    @PostMapping("/aceitar")
    public String aceitarConsentimento(@RequestParam(value = "aceitar", required = false) String aceitar,
                                       HttpSession session,
                                       Model model) {
        // Recupera usuário da sessão (padrão que usamos)
        Usuario usuarioSessao = (Usuario) session.getAttribute("usuarioLogado");

        if (usuarioSessao == null) {
            // sem sessão -> redireciona para login
            return "redirect:/login";
        }

        // O checkbox envia "on" ou valor definido; se nulo, usuário não aceitou
        if (aceitar == null || aceiteFalse(aceitar)) {
            model.addAttribute("error", "Você precisa aceitar a política para continuar.");
            return "consentimento-lgpd";
        }

        // registra consentimento
        usuarioSessao.setConsentimentoLgpd(true);
        usuarioSessao.setDataConsentimentoLgpd(LocalDateTime.now());

        // salva no banco
        usuarioService.salvar(usuarioSessao);

        // atualizar o objeto na sessão (opcional)
        session.setAttribute("usuarioLogado", usuarioSessao);

        // Registrar log da ação
        lgpdLogService.registrarEvento(usuarioSessao.getUsername(),
                "Aceite LGPD", "Usuário aceitou a política de privacidade.");

        // redireciona para a home após aceitar
        return "redirect:/";
    }

    @PostMapping
    public String cadastrarUsuario(@Valid @ModelAttribute("usuario") Usuario usuario,
                                   BindingResult bindingResult,
                                   Model model) {

        // valida consentimento (checkbox obrigatório)
        if (usuario.getConsentimentoLgpd() == null || !usuario.getConsentimentoLgpd()) {
            bindingResult.rejectValue("consentimentoLgpd", "consentimento.required",
                    "Você deve aceitar a Política de Privacidade.");
        }

        // valida username não vazio
        if (usuario.getUsername() == null || usuario.getUsername().isBlank()) {
            bindingResult.rejectValue("username", "username.required", "Username é obrigatório.");
        } else {
            // verifica se já existe outro usuário com mesmo username
            Usuario existente = usuarioService.buscarPorUsername(usuario.getUsername());
            if (existente != null && (usuario.getId() == null || !existente.getId().equals(usuario.getId()))) {
                bindingResult.rejectValue("username", "username.exists", "Username já em uso.");
            }
        }

        if (bindingResult.hasErrors()) {
            model.addAttribute("error", "Corrija os campos em destaque.");
            return "cadastro-usuario";
        }

        // grava timestamp do consentimento
        if (usuario.getConsentimentoLgpd() != null && usuario.getConsentimentoLgpd()) {
            usuario.setDataConsentimentoLgpd(LocalDateTime.now());
        }

        // salva (UsuarioService já faz bcrypt)
        usuarioService.salvar(usuario);

        model.addAttribute("success", true);
        // limpa o formulário mostrando novo objeto
        model.addAttribute("usuario", new Usuario());
        return "cadastro-usuario";
    }

    // utilitário simples para interpretar valores possivelmente enviados
    private boolean aceiteFalse(String aceitar) {
        // interpretar valores que representem false (seguro)
        String v = aceitar.trim().toLowerCase();
        return v.equals("false") || v.equals("0") || v.equals("off") || v.equals("no");
    }

    // LISTAR TODOS OS USUÁRIOS
    @GetMapping("/lista")
    public String listarUsuarios(Model model) {
        model.addAttribute("usuarios", usuarioService.listarTodos());
        return "lista-usuarios";
    }



}
