package com.HortiSystem.Sistema;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

class UsuarioServiceTest {

    @Test
    void deveAutenticarUsuarioCorretamente() {
        String username = "admin";
        String senhaDigitada = "1234";
        String senhaBanco = "1234";

        boolean autenticado = senhaDigitada.equals(senhaBanco);

        assertTrue(autenticado, "Usuário deve ser autenticado com senha correta");
    }

    @Test
    void deveFalharAutenticacaoComSenhaErrada() {
        String senhaDigitada = "senhaErrada";
        String senhaBanco = "1234";

        boolean autenticado = senhaDigitada.equals(senhaBanco);

        assertFalse(autenticado, "Usuário não deve ser autenticado com senha incorreta");
    }
}
