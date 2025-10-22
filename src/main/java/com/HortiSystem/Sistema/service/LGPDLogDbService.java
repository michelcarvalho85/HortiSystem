package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.LogEvento;
import com.HortiSystem.Sistema.repository.LogEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.Optional;

@Service
public class LGPDLogDbService {

    @Autowired
    private LogEventoRepository logRepo;

    /**
     * Registrar evento no banco de dados.
     *
     * @param usuario      nome do usuário (ou "Sistema")
     * @param usuarioId    id do usuário (nullable)
     * @param acao         ação executada (ex: USER.CREATE)
     * @param recurso      recurso afetado (ex: USER, /api/usuario)
     * @param detalhes     detalhes textuais (curto, sanitizado)
     * @param ip           ip do cliente (nullable)
     * @param nivel        nível do evento (INFO, WARN, ERROR, SECURITY)
     * @param correlationId id de correlação (nullable)
     */
    public void registrarEvento(String usuario,
                                Long usuarioId,
                                String acao,
                                String recurso,
                                String detalhes,
                                String ip,
                                String nivel,
                                String correlationId) {
        try {
            LogEvento log = new LogEvento();
            log.setUsuario(usuario != null ? usuario : "Anônimo");
            log.setUsuarioId(usuarioId);
            log.setAcao(acao);
            log.setRecurso(recurso);
            log.setIp(ip);
            log.setDetalhes(detalhes);
            log.setNivel(nivel != null ? nivel : "INFO");
            log.setDataHora(LocalDateTime.now());
            log.setCorrelationId(correlationId);
            logRepo.save(log);
        } catch (Exception ex) {
            // Evitar lançar exceção de logging para não quebrar fluxo da aplicação.
            System.err.println("Erro ao salvar log no DB: " + ex.getMessage());
        }
    }

    // Adicione este método em com.HortiSystem.Sistema.service.LGPDLogDbService
    /**
     * Método compatível com chamadas existentes no projeto: registrar(usuario, usuarioId, acao, recurso, ip, detalhes, nivel)
     * Ele apenas reordena/encaminha os parâmetros para o método registrarEvento(...) existente.
     */
    public void registrar(String usuario,
                          Long usuarioId,
                          String acao,
                          String recurso,
                          String ip,
                          String detalhes,
                          String nivel) {
        // Note: a sua implementação principal espera (usuario, usuarioId, acao, recurso, detalhes, ip, nivel, correlationId)
        // Aqui nós fazemos o re-mapeamento para manter compatibilidade com as chamadas atuais.
        registrarEvento(usuario, usuarioId, acao, recurso, detalhes, ip, nivel, null);
    }

    // Overload simplificado para chamadas que não possuem usuarioId/correlationId
    public void registrarEvento(String usuario, String acao, String recurso, String detalhes, String ip, String nivel) {
        registrarEvento(usuario, null, acao, recurso, detalhes, ip, nivel, null);
    }
}
