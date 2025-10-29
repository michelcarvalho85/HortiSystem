// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema;

import com.HortiSystem.Sistema.model.Produto;
import com.HortiSystem.Sistema.model.Usuario;
import com.HortiSystem.Sistema.repository.UsuarioRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

/**
 * CLASSE PRINCIPAL DA APLICAÇÃO SPRING BOOT
 * Esta é a classe que inicia toda a aplicação Spring Boot
 * A anotação @SpringBootApplication habilita a configuração automática
 */
@SpringBootApplication // Anotação principal que inicia o Spring Boot
public class SistemaApplication {

    /**
     * MÉTODO MAIN - PONTO DE INICIALIZAÇÃO DA APLICAÇÃO
     * Este método é executado quando a aplicação é iniciada
     * @param args - Argumentos de linha de comando
     */
    public static void main(String[] args) {
        // INICIA A APLICAÇÃO SPRING BOOT
        SpringApplication.run(SistemaApplication.class, args);
        // Este método:
        // 1. Inicia o servidor embutido (Tomcat)
        // 2. Configura o contexto Spring
        // 3. Escaneia e configura todos os componentes (@Controller, @Service, etc.)
        // 4. Inicia o banco de dados (se configurado)
    }

}