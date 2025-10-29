package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.LogEvento;
import com.HortiSystem.Sistema.repository.LogEventoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
<<<<<<< HEAD
=======
import java.util.Optional;
>>>>>>> c2bf4e4858473142a2841cf09ba75355b6f8c8b3

@Service
public class LGPDLogDbService {

    @Autowired
<<<<<<< HEAD
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
=======
    private static LogEventoRepository logRepo;

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
    public static void registrarEvento(String usuario,
                                       Long usuarioId,
                                       String acao,
                                       String recurso,
                                       String detalhes,
                                       String ip,
                                       String nivel,
                                       String correlationId) {
>>>>>>> c2bf4e4858473142a2841cf09ba75355b6f8c8b3
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
<<<<<<< HEAD
=======
            // Evitar lançar exceção de logging para não quebrar fluxo da aplicação.
>>>>>>> c2bf4e4858473142a2841cf09ba75355b6f8c8b3
            System.err.println("Erro ao salvar log no DB: " + ex.getMessage());
        }
    }

<<<<<<< HEAD
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
=======
    // Adicione este método em com.HortiSystem.Sistema.service.LGPDLogDbService
    /**
     * Método compatível com chamadas existentes no projeto: registrar(usuario, usuarioId, acao, recurso, ip, detalhes, nivel)
     * Ele apenas reordena/encaminha os parâmetros para o método registrarEvento(...) existente.
     */
    public static void registrar(String usuario,
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

>>>>>>> c2bf4e4858473142a2841cf09ba75355b6f8c8b3
}
