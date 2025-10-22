package com.HortiSystem.Sistema.config;

import com.HortiSystem.Sistema.model.Usuario;
import com.HortiSystem.Sistema.service.UsuarioService;
import com.HortiSystem.Sistema.service.LGPDLogDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.web.authentication.AuthenticationSuccessHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import java.io.IOException;

@Component
public class AuthSuccessHandler implements AuthenticationSuccessHandler {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private LGPDLogDbService lgpdLogDbService;

    @Override
    public void onAuthenticationSuccess(HttpServletRequest request, HttpServletResponse response,
                                        Authentication authentication) throws IOException, ServletException {

        String username = authentication.getName();

        // busca o usuário no banco e coloca na sessão
        Usuario usuario = usuarioService.buscarPorUsername(username);
        if (usuario != null) {
            request.getSession().setAttribute("usuarioLogado", usuario);
        }

        // --- LGPD: Registrar log de login com sucesso no banco ---
        lgpdLogDbService.registrar(
                usuario != null ? usuario.getNome() : "Anônimo",   // ← muda aqui
                usuario != null ? usuario.getId() : null,
                "LOGIN_SUCCESS",
                "auth/login",
                getClientIp(request),
                "Login bem-sucedido no sistema",
                "INFO"
        );


        // Logging adicional (arquivo / console)
        org.slf4j.LoggerFactory.getLogger("LOGIN_LOGGER")
                .info("LOGIN_SUCESSO | usuario: {} | ip: {}", username, getClientIp(request));

        // redireciona para a página inicial
        response.sendRedirect("/");
    }

    private String getClientIp(HttpServletRequest request) {
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null) {
            return xfHeader.split(",")[0];
        }
        return request.getRemoteAddr();
    }
}
