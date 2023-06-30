package com.example.ondesk.model;

import java.util.Calendar;

public class Emprestimo {

    private String idLeitor;
    private String idLivro;
    private String nomeLivro;
    private String nomeLeitor;
    private long dataSaida;
    private long dataPrevista;
    private long dataEntrada;
    private String urlImage;

    public String getUrlImage() {
        return urlImage;
    }

    public void setUrlImage(String urlImage) {
        this.urlImage = urlImage;
    }

    public String getNomeLivro() {
        return nomeLivro;
    }

    public void setNomeLivro(String nomeLivro) {
        this.nomeLivro = nomeLivro;
    }

    public String getNomeLeitor() {
        return nomeLeitor;
    }

    public void setNomeLeitor(String nomeLeitor) {
        this.nomeLeitor = nomeLeitor;
    }

    public String getIdLeitor() {
        return idLeitor;
    }

    public void setIdLeitor(String idLeitor) {
        this.idLeitor = idLeitor;
    }

    public String getIdLivro() {
        return idLivro;
    }

    public void setIdLivro(String idLivro) {
        this.idLivro = idLivro;
    }

    public long getDataSaida() {
        return dataSaida;
    }

    public void setDataSaida(long dataSaida) {
        this.dataSaida = dataSaida;
    }

    public long getDataPrevista() {
        return dataPrevista;
    }

    public void setDataPrevista(long dataPrevista) {
        this.dataPrevista = dataPrevista;
    }

    public long getDataEntrada() {
        return dataEntrada;
    }

    public void setDataEntrada(long dataEntrada) {
        this.dataEntrada = dataEntrada;
    }

}
