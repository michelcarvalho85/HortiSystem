package com.HortiSystem.Sistema.service;

import com.HortiSystem.Sistema.model.Usuario;
import com.HortiSystem.Sistema.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;
    @Autowired
    private LGPDLogService lgpdLogService;



    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

    public Usuario salvar(Usuario usuario) {
        if (usuario.getPassword() != null && !usuario.getPassword().isBlank()) {
            // evita double-encode: verifica prefixos bcrypt
            String pw = usuario.getPassword();
            if (!(pw.startsWith("$2a$") || pw.startsWith("$2b$") || pw.startsWith("$2y$"))) {
                usuario.setPassword(passwordEncoder.encode(pw));
            }
            lgpdLogService.registrarEvento(usuario.getUsername(),
                    "Cadastro de Usuário", "Usuário criado com sucesso.");
        }

        return usuarioRepository.save(usuario);
    }





    public Usuario buscarPorUsername(String username) {
        return usuarioRepository.findByUsername(username).orElse(null);
    }

    public List<Usuario> listarTodos() {
        return usuarioRepository.findAll();
    }

}
