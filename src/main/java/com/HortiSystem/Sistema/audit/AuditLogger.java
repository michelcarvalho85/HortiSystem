package com.HortiSystem.Sistema.audit;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.stereotype.Component;
import jakarta.servlet.http.HttpServletRequest;

@Component
public class AuditLogger {

    private static final Logger auditLogger = LoggerFactory.getLogger("com.HortiSystem.Sistema.audit");

    public void logLogin(String username, boolean success, HttpServletRequest request) {
        try {
            MDC.put("userId", username != null ? username : "Anônimo");
            MDC.put("userIp", getClientIp(request));
            MDC.put("action", "LOGIN_" + (success ? "SUCCESS" : "FAILURE"));

            auditLogger.info("Tentativa de login - Sucesso: {}", success);
        } finally {
            MDC.clear();
        }
    }

    private String getClientIp(HttpServletRequest request) {
        if (request == null) return "unknown";
        String xfHeader = request.getHeader("X-Forwarded-For");
        if (xfHeader != null && !xfHeader.isBlank()) {
            return xfHeader.split(",")[0].trim();
        }
        return request.getRemoteAddr();
    }
}
