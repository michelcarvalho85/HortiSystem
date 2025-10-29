// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.util;

import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * UTILITÁRIO PARA GERAR SENHAS CRIPTOGRAFADAS
 * Esta classe é um utilitário para gerar hashes BCrypt de senhas
 * É usado durante o desenvolvimento para criar senhas seguras para usuários de teste
 * ATENÇÃO: Esta classe não deve ser usada em produção para gerar senhas
 */
public class PasswordGenerator {

    /**
     * MÉTODO PRINCIPAL - GERA HASH BCrypt PARA UMA SENHA
     * Este método é executado independentemente para gerar hashes de senha
     * @param args - Argumentos da linha de comando (não utilizados)
     */
    public static void main(String[] args) {
        // CRIA O ENCODER BCrypt - Algoritmo de criptografia para senhas
        BCryptPasswordEncoder encoder = new BCryptPasswordEncoder();

        // GERAR HASH PARA A SENHA "vendedor123"
        String senhaVendedor = encoder.encode("vendedor123");

        // EXIBE O RESULTADO NO CONSOLE
        System.out.println("=== HASH BCrypt PARA vendedor123 ===");
        System.out.println(senhaVendedor); // Exemplo: $2a$10$X5hFKV3u3P2A7g7Z5bYQ9e...
        System.out.println("=====================================");

        // Este hash gerado deve ser copiado e colocado no banco de dados
        // para o usuário ter a senha "vendedor123"
    }
}