package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
class SegDispositivoId implements Serializable {

    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name = "txt_device_id", length = 128)
    private String txtDeviceId;

    public SegDispositivoId() {
    }

    public SegDispositivoId(Long codUsuario, String txtDeviceId) {
        this.codUsuario = codUsuario;
        this.txtDeviceId = txtDeviceId;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegDispositivoId that)) {
            return false;
        }
        return Objects.equals(codUsuario, that.codUsuario) && Objects.equals(txtDeviceId, that.txtDeviceId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codUsuario, txtDeviceId);
    }

    // Getters y Setters
    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

}

@Entity
@Table(name = "seg_dispositivo")
public class SegDispositivo {

    @EmbeddedId
    private SegDispositivoId id;

    @Column(name = "txt_user_agent", length = 500)
    private String txtUserAgent;

    @Column(name = "txt_ip_primera", length = 45)
    private String txtIpPrimera;

    @Column(name = "fec_primer_uso", nullable = false)
    private LocalDateTime fecPrimerUso;

    @Column(name = "fec_ultimo_uso", nullable = false)
    private LocalDateTime fecUltimoUso;

    @Column(name = "sts_notificado", nullable = false, length = 1)
    private String stsNotificado;

    @Column(name = "sts_dispositivo", nullable = false, length = 1)
    private String stsDispositivo;

    // Getters y Setters
    public SegDispositivoId getId() {
        return id;
    }

    public void setId(SegDispositivoId id) {
        this.id = id;
    }

    public String getTxtUserAgent() {
        return txtUserAgent;
    }

    public void setTxtUserAgent(String txtUserAgent) {
        this.txtUserAgent = txtUserAgent;
    }

    public String getTxtIpPrimera() {
        return txtIpPrimera;
    }

    public void setTxtIpPrimera(String txtIpPrimera) {
        this.txtIpPrimera = txtIpPrimera;
    }

    public LocalDateTime getFecPrimerUso() {
        return fecPrimerUso;
    }

    public void setFecPrimerUso(LocalDateTime fecPrimerUso) {
        this.fecPrimerUso = fecPrimerUso;
    }

    public LocalDateTime getFecUltimoUso() {
        return fecUltimoUso;
    }

    public void setFecUltimoUso(LocalDateTime fecUltimoUso) {
        this.fecUltimoUso = fecUltimoUso;
    }

    public String getStsNotificado() {
        return stsNotificado;
    }

    public void setStsNotificado(String stsNotificado) {
        this.stsNotificado = stsNotificado;
    }

    public String getStsDispositivo() {
        return stsDispositivo;
    }

    public void setStsDispositivo(String stsDispositivo) {
        this.stsDispositivo = stsDispositivo;
    }

}
