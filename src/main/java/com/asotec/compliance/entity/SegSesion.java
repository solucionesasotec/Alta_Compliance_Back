package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seg_sesion")
public class SegSesion {

    @Id
    @Column(name = "id_sesion")
    private UUID idSesion;

    @Column(name = "cod_usuario", nullable = false)
    private Long codUsuario;

    @Column(name = "cod_institucion", nullable = false)
    private Integer codInstitucion;

    @Column(name = "txt_device_id", nullable = false, length = 128)
    private String txtDeviceId;

    @Column(name = "txt_ip", nullable = false, length = 45)
    private String txtIp;

    @Column(name = "txt_user_agent", length = 500)
    private String txtUserAgent;

    @Column(name = "sts_mfa_validado", nullable = false, length = 1)
    private String stsMfaValidado;

    @Column(name = "fec_inicio", nullable = false)
    private LocalDateTime fecInicio;

    @Column(name = "fec_ultima_actividad", nullable = false)
    private LocalDateTime fecUltimaActividad;

    @Column(name = "fec_expira", nullable = false)
    private LocalDateTime fecExpira;

    @Column(name = "fec_fin")
    private LocalDateTime fecFin;

    @Column(name = "sts_sesion", nullable = false, length = 1)
    private String stsSesion;

    // Getters y Setters
    public UUID getIdSesion() {
        return idSesion;
    }

    public void setIdSesion(UUID idSesion) {
        this.idSesion = idSesion;
    }

    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Integer getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Integer codInstitucion) {
        this.codInstitucion = codInstitucion;
    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

    public String getTxtIp() {
        return txtIp;
    }

    public void setTxtIp(String txtIp) {
        this.txtIp = txtIp;
    }

    public String getTxtUserAgent() {
        return txtUserAgent;
    }

    public void setTxtUserAgent(String txtUserAgent) {
        this.txtUserAgent = txtUserAgent;
    }

    public String getStsMfaValidado() {
        return stsMfaValidado;
    }

    public void setStsMfaValidado(String stsMfaValidado) {
        this.stsMfaValidado = stsMfaValidado;
    }

    public LocalDateTime getFecInicio() {
        return fecInicio;
    }

    public void setFecInicio(LocalDateTime fecInicio) {
        this.fecInicio = fecInicio;
    }

    public LocalDateTime getFecUltimaActividad() {
        return fecUltimaActividad;
    }

    public void setFecUltimaActividad(LocalDateTime fecUltimaActividad) {
        this.fecUltimaActividad = fecUltimaActividad;
    }

    public LocalDateTime getFecExpira() {
        return fecExpira;
    }

    public void setFecExpira(LocalDateTime fecExpira) {
        this.fecExpira = fecExpira;
    }

    public LocalDateTime getFecFin() {
        return fecFin;
    }

    public void setFecFin(LocalDateTime fecFin) {
        this.fecFin = fecFin;
    }

    public String getStsSesion() {
        return stsSesion;
    }

    public void setStsSesion(String stsSesion) {
        this.stsSesion = stsSesion;
    }

}
