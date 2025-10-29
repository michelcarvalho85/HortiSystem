package com.HortiSystem.Sistema.service;

import org.springframework.stereotype.Service;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;

@Service
public class LGPDLogService {

    private static final String LOG_FILE = "logs/atividade_sistema.txt";
    private static final DateTimeFormatter FORMATTER =
            DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");

    public void registrarEvento(String usuario, String acao, String detalhes) {
        String dataHora = LocalDateTime.now().format(FORMATTER);
        String log = String.format("[%s] Usuário: %s | Ação: %s | Detalhes: %s%n",
                dataHora, usuario, acao, detalhes);

        try (FileWriter fw = new FileWriter(LOG_FILE, true)) {
            fw.write(log);
        } catch (IOException e) {
            System.err.println("Erro ao gravar log LGPD: " + e.getMessage());
        }
    }
}
