package com.HortiSystem.Sistema.model;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

@Entity
@Getter
@Setter
@Table(name = "usuario")
public class Usuario implements UserDetails {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true, nullable = false)
    private String username;



    @Column(nullable = false)
    private String password; // senha criptografada

    private String email;

    private String role;

    private boolean ativo = true;

    // Consentimento LGPD
    private Boolean consentimentoLgpd = false;

    private LocalDateTime dataConsentimentoLgpd;

    // Versão da política (opcional)
    private String politicaVersao;

    // UserDetails implementations
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + (role == null ? "USER" : role)));
    }

    @Override
    public String getPassword() { return this.password; }

    @Override
    public String getUsername() { return this.username; }

    public String getNome() { return "nome"; }

    @Override
    public boolean isAccountNonExpired() { return true; }

    @Override
    public boolean isAccountNonLocked() { return true; }

    @Override
    public boolean isCredentialsNonExpired() { return true; }

    @Override
    public boolean isEnabled() { return this.ativo; }

    // setter de dataConsentimento (Lombok também gera, mas deixamos explícito)

    public void setDataConsentimentoLgpd(LocalDateTime dataConsentimentoLgpd) {
        this.dataConsentimentoLgpd = dataConsentimentoLgpd;
    }


}

