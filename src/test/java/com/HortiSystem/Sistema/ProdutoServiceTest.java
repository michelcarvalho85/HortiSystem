package com.HortiSystem.Sistema;

import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.assertEquals;

class ProdutoServiceTest {

    @Test
    void deveSomarDoisNumerosCorretamente() {
        int resultado = 2 + 2;
        assertEquals(4, resultado, "A soma de 2 + 2 deve ser igual a 4");
    }
}
