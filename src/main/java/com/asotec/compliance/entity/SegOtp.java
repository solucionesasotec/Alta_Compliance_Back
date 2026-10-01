package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seg_otp")
public class SegOtp {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_otp")
    private Long numOtp;

    @Column(name = "cod_usuario", nullable = false)
    private Long codUsuario;

    @Column(name = "id_sesion")
    private UUID idSesion;

    @Column(name = "cod_proposito", nullable = false, length = 20)
    private String codProposito;

    @Column(name = "txt_hash_codigo", nullable = false, length = 255)
    private String txtHashCodigo;

    @Column(name = "txt_canal", nullable = false, length = 10)
    private String txtCanal;

    @Column(name = "txt_destino_mask", length = 30)
    private String txtDestinoMask;

    @Column(name = "fec_generado", nullable = false)
    private LocalDateTime fecGenerado;

    @Column(name = "fec_expira", nullable = false)
    private LocalDateTime fecExpira;

    @Column(name = "num_intentos", nullable = false)
    private Integer numIntentos;

    @Column(name = "num_reenvios", nullable = false)
    private Integer numReenvios;

    @Column(name = "fec_validado")
    private LocalDateTime fecValidado;

    @Column(name = "sts_otp", nullable = false, length = 1)
    private String stsOtp;

    // Getters y Setters
    public Long getNumOtp() {
        return numOtp;
    }

    public void setNumOtp(Long numOtp) {
        this.numOtp = numOtp;
    }

    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public UUID getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(UUID idSesion) {
        this.idSesion = idSesion;
    }

    public String getCodProposito() {
        return codProposito;
    }

    public void setCodProposito(String codProposito) {
        this.codProposito = codProposito;
    }

    public String getTxtHashCodigo() {
        return txtHashCodigo;
    }

    public void setTxtHashCodigo(String txtHashCodigo) {
        this.txtHashCodigo = txtHashCodigo;
    }

    public String getTxtCanal() {
        return txtCanal;
    }

    public void setTxtCanal(String txtCanal) {
        this.txtCanal = txtCanal;
    }

    public String getTxtDestinoMask() {
        return txtDestinoMask;
    }

    public void setTxtDestinoMask(String txtDestinoMask) {
        this.txtDestinoMask = txtDestinoMask;
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

    public Integer getNumIntentos() {
        return numIntentos;
    }

    public void setNumIntentos(Integer numIntentos) {
        this.numIntentos = numIntentos;
    }

    public Integer getNumReenvios() {
        return numReenvios;
    }

    public void setNumReenvios(Integer numReenvios) {
        this.numReenvios = numReenvios;
    }

    public LocalDateTime getFecValidado() {
        return fecValidado;
    }

    public void setFecValidado(LocalDateTime fecValidado) {
        this.fecValidado = fecValidado;
    }

    public String getStsOtp() {
        return stsOtp;
    }

    public void setStsOtp(String stsOtp) {
        this.stsOtp = stsOtp;
    }

}
