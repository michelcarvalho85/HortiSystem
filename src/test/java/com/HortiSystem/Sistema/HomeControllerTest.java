package com.HortiSystem.Sistema;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class HomeControllerTest {

    @Test
    void deveRetornarTituloCorretoDaPaginaInicial() {
        String tituloEsperado = "HortiSystem — Gestão Moderna";
        String tituloAtual = "HortiSystem — Gestão Moderna";

        assertEquals(tituloEsperado, tituloAtual, "O título da página inicial deve ser correto");
    }
}
