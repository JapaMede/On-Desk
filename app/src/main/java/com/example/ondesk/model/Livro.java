package com.example.ondesk.model;

import java.io.Serializable;

public class Livro implements Serializable {


    private String id;
    private String Nome;
    private String Categoria;
    private String Autor;
    private Long dataPublicacao;
    private boolean status;
    private String urlImage;
    private String sinopse;

    public String getSinopse() {
        return sinopse;
    }

    public void setSinopse(String sinopse) {
        this.sinopse = sinopse;
    }

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public boolean isStatus() {
        return status;
    }

    public void setStatus(boolean status) {
        this.status = status;
    }
    public Long getDataPublicacao() {
        return dataPublicacao;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void setDataPublicacao(Long dataPublicacao) {
        this.dataPublicacao = dataPublicacao;
    }

    public String getNome() {
        return Nome;
    }

    public void setNome(String nome) {
        Nome = nome;
    }

    public String getCategoria() {
        return Categoria;
    }

    public void setCategoria(String categoria) {
        Categoria = categoria;
    }

    public String getAutor() {
        return Autor;
    }

    public void setAutor(String autor) {
        Autor = autor;
    }
}