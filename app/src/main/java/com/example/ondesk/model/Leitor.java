package com.example.ondesk.model;

public class Leitor {
    private String id;
    private String email;
    private String senha;
    private String nome;
    private boolean Verificado;

    public boolean isVerificado() {
        return Verificado;
    }

    public void setVerificado(boolean verificado) {
        Verificado = verificado;
    }

    public String getNome() {
        return nome;
    }

    public void setNome(String nome) {
        this.nome = nome;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getSenha() {
        return senha;
    }

    public void setSenha(String senha) {
        this.senha = senha;
    }
}
