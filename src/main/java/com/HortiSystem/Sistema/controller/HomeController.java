// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

/**
 * CONTROLADOR DA PÁGINA INICIAL - GERENCIA A HOME DO SISTEMA
 * Esta classe controla o acesso à página principal após o login
 */
@Controller // Indica que esta classe é um controlador Spring MVC
public class HomeController {

    /**
     * MÉTODO: EXIBIR PÁGINA INICIAL
     * URL: GET /
     * FUNÇÃO: Renderiza a página inicial (dashboard) após o login bem-sucedido
     * @return String - Nome do template HTML da página inicial
     */
    @GetMapping("/")
    public String index() {
        return "index"; // Renderiza o arquivo templates/index.html
        // Esta é a página principal que os usuários veem após fazer login
    }
}