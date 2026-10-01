package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_politica")
public class SegPolitica {

    @Id
    @Column(name = "cod_institucion")
    private Integer codInstitucion;

    @Column(name = "num_clave_long_min", nullable = false)
    private Integer numClaveLongMin;

    @Column(name = "sts_req_mayuscula", nullable = false, length = 1)
    private String stsReqMayuscula;

    @Column(name = "sts_req_minuscula", nullable = false, length = 1)
    private String stsReqMinuscula;

    @Column(name = "sts_req_numero", nullable = false, length = 1)
    private String stsReqNumero;

    @Column(name = "sts_req_simbolo", nullable = false, length = 1)
    private String stsReqSimbolo;

    @Column(name = "num_clave_dias_vigencia", nullable = false)
    private Integer numClaveDiasVigencia;

    @Column(name = "num_clave_historial", nullable = false)
    private Integer numClaveHistorial;

    @Column(name = "num_intentos_max", nullable = false)
    private Integer numIntentosMax;

    @Column(name = "num_min_bloqueo", nullable = false)
    private Integer numMinBloqueo;

    @Column(name = "num_min_inactividad", nullable = false)
    private Integer numMinInactividad;

    @Column(name = "num_min_sesion_max", nullable = false)
    private Integer numMinSesionMax;

    @Column(name = "sts_mfa_obligatorio", nullable = false, length = 1)
    private String stsMfaObligatorio;

    @Column(name = "num_otp_seg_vigencia", nullable = false)
    private Integer numOtpSegVigencia;

    @Column(name = "num_otp_intentos_max", nullable = false)
    private Integer numOtpIntentosMax;

    @Column(name = "num_otp_reenvios_max", nullable = false)
    private Integer numOtpReenviosMax;

    @Column(name = "sts_cierre_cambio_disp", nullable = false, length = 1)
    private String stsCierreCambioDisp;

    @Column(name = "sts_cierre_cambio_red", nullable = false, length = 1)
    private String stsCierreCambioRed;

    @Column(name = "cod_usrmod")
    private Long codUsrmod;

    @Column(name = "fec_usrmod", nullable = false)
    private LocalDateTime fecUsrmod;

    // Getters y Setters

    public Integer getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Integer codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    public Integer getNumClaveLongMin() {
        return numClaveLongMin;
    }

    public void setNumClaveLongMin(Integer numClaveLongMin) {
        this.numClaveLongMin = numClaveLongMin;
    }

    public String getStsReqMayuscula() {
        return stsReqMayuscula;
    }

    public void setStsReqMayuscula(String stsReqMayuscula) {
        this.stsReqMayuscula = stsReqMayuscula;
    }

    public String getStsReqMinuscula() {
        return stsReqMinuscula;
    }

    public void setStsReqMinuscula(String stsReqMinuscula) {
        this.stsReqMinuscula = stsReqMinuscula;
    }

    public String getStsReqNumero() {
        return stsReqNumero;
    }

    public void setStsReqNumero(String stsReqNumero) {
        this.stsReqNumero = stsReqNumero;
    }

    public String getStsReqSimbolo() {
        return stsReqSimbolo;
    }

    public void setStsReqSimbolo(String stsReqSimbolo) {
        this.stsReqSimbolo = stsReqSimbolo;
    }

    public Integer getNumClaveDiasVigencia() {
        return numClaveDiasVigencia;
    }

    public void setNumClaveDiasVigencia(Integer numClaveDiasVigencia) {
        this.numClaveDiasVigencia = numClaveDiasVigencia;
    }

    public Integer getNumClaveHistorial() {
        return numClaveHistorial;
    }

    public void setNumClaveHistorial(Integer numClaveHistorial) {
        this.numClaveHistorial = numClaveHistorial;
    }

    public Integer getNumIntentosMax() {
        return numIntentosMax;
    }

    public void setNumIntentosMax(Integer numIntentosMax) {
        this.numIntentosMax = numIntentosMax;
    }

    public Integer getNumMinBloqueo() {
        return numMinBloqueo;
    }

    public void setNumMinBloqueo(Integer numMinBloqueo) {
        this.numMinBloqueo = numMinBloqueo;
    }

    public Integer getNumMinInactividad() {
        return numMinInactividad;
    }

    public void setNumMinInactividad(Integer numMinInactividad) {
        this.numMinInactividad = numMinInactividad;
    }

    public Integer getNumMinSesionMax() {
        return numMinSesionMax;
    }

    public void setNumMinSesionMax(Integer numMinSesionMax) {
        this.numMinSesionMax = numMinSesionMax;
    }

    public String getStsMfaObligatorio() {
        return stsMfaObligatorio;
    }

    public void setStsMfaObligatorio(String stsMfaObligatorio) {
        this.stsMfaObligatorio = stsMfaObligatorio;
    }

    public Integer getNumOtpSegVigencia() {
        return numOtpSegVigencia;
    }

    public void setNumOtpSegVigencia(Integer numOtpSegVigencia) {
        this.numOtpSegVigencia = numOtpSegVigencia;
    }

    public Integer getNumOtpIntentosMax() {
        return numOtpIntentosMax;
    }

    public void setNumOtpIntentosMax(Integer numOtpIntentosMax) {
        this.numOtpIntentosMax = numOtpIntentosMax;
    }

    public Integer getNumOtpReenviosMax() {
        return numOtpReenviosMax;
    }

    public void setNumOtpReenviosMax(Integer numOtpReenviosMax) {
        this.numOtpReenviosMax = numOtpReenviosMax;
    }

    public String getStsCierreCambioDisp() {
        return stsCierreCambioDisp;
    }

    public void setStsCierreCambioDisp(String stsCierreCambioDisp) {
        this.stsCierreCambioDisp = stsCierreCambioDisp;
    }

    public String getStsCierreCambioRed() {
        return stsCierreCambioRed;
    }

    public void setStsCierreCambioRed(String stsCierreCambioRed) {
        this.stsCierreCambioRed = stsCierreCambioRed;
    }

    public Long getCodUsrmod() {
        return codUsrmod;
    }

    public void setCodUsrmod(Long codUsrmod) {
        this.codUsrmod = codUsrmod;
    }

    public LocalDateTime getFecUsrmod() {
        return fecUsrmod;
    }

    public void setFecUsrmod(LocalDateTime fecUsrmod) {
        this.fecUsrmod = fecUsrmod;
    }
   
}