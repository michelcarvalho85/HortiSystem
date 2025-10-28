package com.HortiSystem.Sistema.config;

import com.HortiSystem.Sistema.service.LGPDLogDbService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

/**
 * Interceptor Spring MVC para registar acessos HTTP relevantes.
 * Registra somente URIs que não sejam arquivos estáticos.
 */
@Component
public class LogInterceptor implements HandlerInterceptor {

    @Autowired
    private LGPDLogDbService logService;

    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String uri = request.getRequestURI();

        if (!uri.startsWith("/css") && !uri.startsWith("/js") && !uri.startsWith("/images") && !uri.startsWith("/webjars")) {
            String ip = request.getHeader("X-Forwarded-For");
            if (ip == null) ip = request.getRemoteAddr();

            // Chamada usando a sobrecarga curta (sem usuarioId)
            logService.registrarEvento(
                    "Sistema",
                    "HTTP.ACCESS",
                    uri,
                    "Acesso a " + uri + " método " + request.getMethod(),
                    ip,
                    "INFO"
            );
        }
        return true;
    }
}
