package com.HortiSystem.Sistema.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LGPDRoutesController {

    @GetMapping("/politica-privacidade")
    public String politicaPrivacidade() {
        return "politica-privacidade";
    }

    @GetMapping("/politica-cookies")
    public String politicaCookies() {
        return "politica-cookies";
    }

    @GetMapping("/termos-uso")
    public String termosUso() {
        return "termos-uso";
    }

    @GetMapping("/consentimento-lgpd")
    public String consentimentoLgpd() {
        return "consentimento-lgpd";
    }
}
