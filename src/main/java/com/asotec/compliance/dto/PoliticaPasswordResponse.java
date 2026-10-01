package com.asotec.compliance.dto;

public class PoliticaPasswordResponse {

    private Integer longitudMinima;
    private Integer longitudMaxima;
    private boolean requiereMayuscula;
    private boolean requiereMinuscula;
    private boolean requiereNumero;
    private boolean requiereSimbolo;
    private Integer noRepetirUltimas;
    private Integer diasVigencia;

    // Constructores, Getters y Setters
    public PoliticaPasswordResponse() {
    }

    public PoliticaPasswordResponse(Integer longitudMinima, Integer longitudMaxima, boolean requiereMayuscula,
            boolean requiereMinuscula, boolean requiereNumero, boolean requiereSimbolo,
            Integer noRepetirUltimas, Integer diasVigencia) {
        this.longitudMinima = longitudMinima;
        this.longitudMaxima = longitudMaxima;
        this.requiereMayuscula = requiereMayuscula;
        this.requiereMinuscula = requiereMinuscula;
        this.requiereNumero = requiereNumero;
        this.requiereSimbolo = requiereSimbolo;
        this.noRepetirUltimas = noRepetirUltimas;
        this.diasVigencia = diasVigencia;
    }

    public Integer getLongitudMinima() {
        return longitudMinima;
    }

    public void setLongitudMinima(Integer longitudMinima) {
        this.longitudMinima = longitudMinima;
    }

    public Integer getLongitudMaxima() {
        return longitudMaxima;
    }

    public void setLongitudMaxima(Integer longitudMaxima) {
        this.longitudMaxima = longitudMaxima;
    }

    public boolean isRequiereMayuscula() {
        return requiereMayuscula;
    }

    public void setRequiereMayuscula(boolean requiereMayuscula) {
        this.requiereMayuscula = requiereMayuscula;
    }

    public boolean isRequiereMinuscula() {
        return requiereMinuscula;
    }

    public void setRequiereMinuscula(boolean requiereMinuscula) {
        this.requiereMinuscula = requiereMinuscula;
    }

    public boolean isRequiereNumero() {
        return requiereNumero;
    }

    public void setRequiereNumero(boolean requiereNumero) {
        this.requiereNumero = requiereNumero;
    }

    public boolean isRequiereSimbolo() {
        return requiereSimbolo;
    }

    public void setRequiereSimbolo(boolean requiereSimbolo) {
        this.requiereSimbolo = requiereSimbolo;
    }

    public Integer getNoRepetirUltimas() {
        return noRepetirUltimas;
    }

    public void setNoRepetirUltimas(Integer noRepetirUltimas) {
        this.noRepetirUltimas = noRepetirUltimas;
    }

    public Integer getDiasVigencia() {
        return diasVigencia;
    }

    public void setDiasVigencia(Integer diasVigencia) {
        this.diasVigencia = diasVigencia;
    }
}
