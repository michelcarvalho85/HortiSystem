// PACOTE E IMPORTAÇÕES
package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.Usuario;
import com.HortiSystem.Sistema.repository.UsuarioRepository;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * SERVIÇO DE AUTENTICAÇÃO PERSONALIZADO - IMPLEMENTA USERDETAILS SERVICE
 * Esta classe é responsável por carregar os detalhes do usuário durante o processo de autenticação
 * do Spring Security. Ela busca o usuário no banco de dados pelo username.
 */
@Service // Indica que esta é uma classe de serviço gerenciada pelo Spring
public class CustomUserDetailsService implements UserDetailsService {

    // REPOSITÓRIO DE USUÁRIOS - Para operações de banco de dados
    private final UsuarioRepository usuarioRepository;

    // LOGGER - Para registrar informações de autenticação no console
    private static final Logger logger = LoggerFactory.getLogger(CustomUserDetailsService.class);

    /**
     * CONSTRUTOR COM INJEÇÃO DE DEPENDÊNCIA
     * O Spring injeta automaticamente o UsuarioRepository
     */
    public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
        this.usuarioRepository = usuarioRepository;
    }

    /**
     * MÉTODO PRINCIPAL - CARREGA USUÁRIO PELO USERNAME
     * Este método é chamado automaticamente pelo Spring Security durante o login
     * @param username - Nome de usuário fornecido no formulário de login
     * @return UserDetails - Objeto com informações do usuário para autenticação
     * @throws UsernameNotFoundException - Se o usuário não for encontrado
     */
    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        // LOGGING - Registra a tentativa de login para auditoria
        logger.info("=== TENTATIVA DE LOGIN ===");
        logger.info("Usuário: {}", username);
        logger.info("Horário: {}", java.time.LocalDateTime.now());

        // BUSCA NO BANCO DE DADOS - Procura usuário pelo username
        Optional<Usuario> usuarioOpt = usuarioRepository.findByUsername(username);

        // VERIFICA SE USUÁRIO EXISTE
        if (usuarioOpt.isEmpty()) {
            // LOGGING DE FALHA - Usuário não encontrado
            logger.error("RESULTADO: FALHA - Usuário não existe: {}", username);
            logger.info("=========================");
            throw new UsernameNotFoundException("Usuário não encontrado: " + username);
        }

        // USUÁRIO ENCONTRADO - Extrai do Optional
        Usuario usuario = usuarioOpt.get();

        // LOGGING DE SUCESSO - Usuário encontrado com detalhes
        logger.info("RESULTADO: SUCESSO - Usuário encontrado");
        logger.info("Role: {}", usuario.getRole());
        logger.info("Email: {}", usuario.getEmail());
        logger.info("=========================");

        // RETORNA O USUÁRIO - A própria classe Usuario implementa UserDetails
        return usuario;
    }
}