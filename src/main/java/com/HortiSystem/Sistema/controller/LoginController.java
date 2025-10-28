// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.controller;

import com.HortiSystem.Sistema.audit.AuditLogger;
import jakarta.servlet.http.HttpServletRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLADOR DE LOGIN - GERENCIA A PÁGINA DE LOGIN
 * Esta classe controla o acesso à página de autenticação do sistema
 */
@Controller // Indica que esta classe é um controlador Spring MVC
public class LoginController {

    /**
     * MÉTODO: EXIBIR PÁGINA DE LOGIN
     * URL: GET /login
     * FUNÇÃO: Renderiza a página de login para autenticação do usuário
     * @return String - Nome do template HTML que será renderizado
     */
    @GetMapping("/login")
    public String login() {
        return "login"; // Renderiza o arquivo templates/login.html
        // O Spring Boot automaticamente procura em src/main/resources/templates/
    }
}