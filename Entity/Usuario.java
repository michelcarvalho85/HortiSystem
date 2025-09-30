package org.example.entity;

    /**
     * ENTITY - Representa um usuário do sistema
     * Contém dados de login e permissões básicas
     */
    public class Usuario {
        private Integer id;
        private String username;
        private String password;
        private String nome;
        private boolean ativo;
        private String perfil; // "ADMIN", "GERENTE", "OPERADOR"

        /**
         * Construtor completo do usuário
         */
        public Usuario(Integer id, String username, String password,
                       String nome, boolean ativo, String perfil) {
            this.id = id;
            this.username = username;
            this.password = password;
            this.nome = nome;
            this.ativo = ativo;
            this.perfil = perfil;
        }

        /**
         * Valida se os dados básicos do usuário estão corretos
         * @return true se dados são válidos, false caso contrário
         */
        public boolean validarDados() {
            if (username == null || username.isBlank()) return false;
            if (password == null || password.length() < 4) return false;
            if (nome == null || nome.isBlank()) return false;
            if (perfil == null || perfil.isBlank()) return false;
            return true;
        }

        /**
         * Método para autenticar o usuário
         * @param username Login informado
         * @param password Senha informada
         * @return true se autenticação for bem-sucedida
         */
        public boolean autenticar(String username, String password) {
            return this.ativo &&
                    this.username.equals(username) &&
                    this.password.equals(password);
        }

        /**
         * Verifica se usuário tem perfil de administrador
         */
        public boolean isAdmin() {
            return "ADMIN".equals(perfil);
        }

        /**
         * Verifica se usuário tem perfil de gerente
         */
        public boolean isGerente() {
            return "GERENTE".equals(perfil) || isAdmin();
        }

        // ============= GETTERS E SETTERS =============
        public Integer getId() { return id; }
        public void setId(Integer id) { this.id = id; }

        public String getUsername() { return username; }
        public void setUsername(String username) { this.username = username; }

        public String getPassword() { return password; }
        public void setPassword(String password) { this.password = password; }

        public String getNome() { return nome; }
        public void setNome(String nome) { this.nome = nome; }

        public boolean isAtivo() { return ativo; }
        public void setAtivo(boolean ativo) { this.ativo = ativo; }

        public String getPerfil() { return perfil; }
        public void setPerfil(String perfil) { this.perfil = perfil; }
    }

