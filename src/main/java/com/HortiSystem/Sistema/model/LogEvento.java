package com.HortiSystem.Sistema.model;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "log_eventos")
public class LogEvento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String usuario;      // username ou "Sistema"
    private Long usuarioId;      // opcional (nullable)
    private String acao;         // ex: USER.CREATE, LOGIN.FAIL
    private String recurso;      // ex: USER, ORDER, /api/login
    @Column(length = 2000)
    private String detalhes;     // JSON ou texto resumido (sanitizado)
    private String ip;
    private String nivel;        // INFO / WARN / ERROR / SECURITY
    private LocalDateTime dataHora;
    private String correlationId; // opcional

    // Getters e setters
    public Long getId() { return id; }

    public String getUsuario() { return usuario; }
    public void setUsuario(String usuario) { this.usuario = usuario; }

    public Long getUsuarioId() { return usuarioId; }
    public void setUsuarioId(Long usuarioId) { this.usuarioId = usuarioId; }

    public String getAcao() { return acao; }
    public void setAcao(String acao) { this.acao = acao; }

    public String getRecurso() { return recurso; }
    public void setRecurso(String recurso) { this.recurso = recurso; }

    public String getDetalhes() { return detalhes; }
    public void setDetalhes(String detalhes) { this.detalhes = detalhes; }

    public String getIp() { return ip; }
    public void setIp(String ip) { this.ip = ip; }

    public String getNivel() { return nivel; }
    public void setNivel(String nivel) { this.nivel = nivel; }

    public LocalDateTime getDataHora() { return dataHora; }
    public void setDataHora(LocalDateTime dataHora) { this.dataHora = dataHora; }

    public String getCorrelationId() { return correlationId; }
    public void setCorrelationId(String correlationId) { this.correlationId = correlationId; }
}
