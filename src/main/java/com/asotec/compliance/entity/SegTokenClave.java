package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_token_clave")
public class SegTokenClave {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_token")
    private Long numToken;

    @Column(name = "cod_usuario", nullable = false)
    private Long codUsuario;

    @Column(name = "txt_hash_token", nullable = false, length = 255)
    private String txtHashToken;

    @Column(name = "cod_origen", nullable = false, length = 1)
    private String codOrigen;

    @Column(name = "cod_usuario_solicita")
    private Long codUsuarioSolicita;

    @Column(name = "fec_generado", nullable = false)
    private LocalDateTime fecGenerado;

    @Column(name = "fec_expira", nullable = false)
    private LocalDateTime fecExpira;

    @Column(name = "fec_usado")
    private LocalDateTime fecUsado;

    @Column(name = "txt_ip_uso", length = 45)
    private String txtIpUso;

    @Column(name = "sts_token", nullable = false, length = 1)
    private String stsToken;

    // Getters y Setters
    public Long getNumToken() {
        return numToken;
    }

    public void setNumToken(Long numToken) {
        this.numToken = numToken;
    }

    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getTxtHashToken() {
        return txtHashToken;
    }

    public void setTxtHashToken(String txtHashToken) {
        this.txtHashToken = txtHashToken;
    }

    public String getCodOrigen() {
        return codOrigen;
    }

    public void setCodOrigen(String codOrigen) {
        this.codOrigen = codOrigen;
    }

    public Long getCodUsuarioSolicita() {
        return codUsuarioSolicita;
    }

    public void setCodUsuarioSolicita(Long codUsuarioSolicita) {
        this.codUsuarioSolicita = codUsuarioSolicita;
    }

    public LocalDateTime getFecGenerado() {
        return fecGenerado;
    }

    public void setFecGenerado(LocalDateTime fecGenerado) {
        this.fecGenerado = fecGenerado;
    }

    public LocalDateTime getFecExpira() {
        return fecExpira;
    }

    public void setFecExpira(LocalDateTime fecExpira) {
        this.fecExpira = fecExpira;
    }

    public LocalDateTime getFecUsado() {
        return fecUsado;
    }

    public void setFecUsado(LocalDateTime fecUsado) {
        this.fecUsado = fecUsado;
    }

    public String getTxtIpUso() {
        return txtIpUso;
    }

    public void setTxtIpUso(String txtIpUso) {
        this.txtIpUso = txtIpUso;
    }

    public String getStsToken() {
        return stsToken;
    }

    public void setStsToken(String stsToken) {
        this.stsToken = stsToken;
    }

}
