package com.HortiSystem.Sistema.config;

import com.HortiSystem.Sistema.model.Usuario;
import com.HortiSystem.Sistema.service.LGPDLogDbService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class ConsentimentoInterceptor implements HandlerInterceptor {

    @Autowired
    private LGPDLogDbService lgpdLogDbService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        String uri = request.getRequestURI();

        // Ignorar recursos estáticos e rotas públicas importantes
        if (uri.startsWith("/css") || uri.startsWith("/js") || uri.startsWith("/images")
                || uri.startsWith("/politica-privacidade") || uri.startsWith("/usuario/aceitar")
                || uri.startsWith("/usuario/novo") || uri.startsWith("/login") || uri.startsWith("/error")) {
            return true;
        }

        // Primeiro, tentar buscar usuário pela sessão (fluxo legado)
        Usuario usuario = (Usuario) request.getSession().getAttribute("usuarioLogado");

        // Se não encontrou na sessão, tentar obter via SecurityContext (se estiver usando Spring Security)
        if (usuario == null) {
            // evitar dependência direta de Security aqui (import se usar)
            try {
                var auth = org.springframework.security.core.context.SecurityContextHolder.getContext().getAuthentication();
                if (auth != null && auth.isAuthenticated() && !"anonymousUser".equals(auth.getName())) {
                    // se usa UsuarioService para carregar, poderia carregar aqui; porém não vamos forçar
                    // para compatibilidade, apenas permitimos continuar e deixar o controller checar consentimento
                    // (ou você pode injetar UsuarioService e carregar o usuário a partir do username)
                }
            } catch (Throwable ignored) { /* se não usar Security, ignora */ }
        }

        // Se tem usuário em sessão e ainda não aceitou a LGPD, bloqueia e redireciona
        if (usuario != null && (usuario.getConsentimentoLgpd() == null || !usuario.getConsentimentoLgpd())) {
            // registrar tentativa de acesso a recurso antes do aceite (registro minimalista)
            try {
                String username = usuario.getUsername() != null ? usuario.getUsername() : "unknown";
                String detalhes = "Acesso a " + uri + " bloqueado até aceite da LGPD";
                lgpdLogDbService.registrar(username, usuario.getId(), "BLOQUEIO_SEM_CONSENTIMENTO", uri, request.getRemoteAddr(), detalhes, "WARN");
            } catch (Exception e) {
                // não falhar por causa do log
            }

            // redireciona para a página de consentimento do usuário
            response.sendRedirect(request.getContextPath() + "/usuario/consentimento");
            return false;
        }

        return true;
    }
}
