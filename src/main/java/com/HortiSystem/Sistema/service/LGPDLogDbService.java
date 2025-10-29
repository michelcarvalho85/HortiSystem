package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.LogEvento;
import com.HortiSystem.Sistema.repository.LogEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;

@Service
public class LGPDLogDbService {

    @Autowired
    private LogEventoRepository logRepo;

    /**
     * Método principal de registro de log
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
            System.err.println("Erro ao salvar log no DB: " + ex.getMessage());
        }
    }

    /**
     * Método de compatibilidade com chamadas existentes
     */
    public void registrar(String usuario,
                          Long usuarioId,
                          String acao,
                          String recurso,
                          String ip,
                          String detalhes,
                          String nivel) {
        registrarEvento(usuario, usuarioId, acao, recurso, detalhes, ip, nivel, null);
    }

    /**
     * Versão simplificada
     */
    public void registrarEvento(String usuario,
                                String acao,
                                String recurso,
                                String detalhes,
                                String ip,
                                String nivel) {
        registrarEvento(usuario, null, acao, recurso, detalhes, ip, nivel, null);
    }
}
