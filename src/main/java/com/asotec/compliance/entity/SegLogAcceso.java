package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seg_log_acceso")
public class SegLogAcceso {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_log")
    private Long numLog;

    @Column(name = "fec_evento", nullable = false)
    private LocalDateTime fecEvento;

    @Column(name = "nom_login_intento", nullable = false, length = 60)
    private String nomLoginIntento;

    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name = "id_sesion")
    private UUID idSesion;

    @Column(name = "cod_evento", nullable = false, length = 20)
    private String codEvento;

    @Column(name = "cod_resultado", nullable = false, length = 30)
    private String codResultado;

    @Column(name = "txt_ip", nullable = false, length = 45)
    private String txtIp;

    @Column(name = "txt_device_id", length = 128)
    private String txtDeviceId;

    @Column(name = "txt_user_agent", length = 500)
    private String txtUserAgent;

    @Column(name = "sts_dispositivo_nuevo", nullable = false, length = 1)
    private String stsDispositivoNuevo;

    public SegLogAcceso() {
    }

    public SegLogAcceso(Long numLog, LocalDateTime fecEvento, String nomLoginIntento, Long codUsuario, UUID idSesion, String codEvento, String codResultado, String txtIp, String txtDeviceId, String txtUserAgent, String stsDispositivoNuevo) {
        this.numLog = numLog;
        this.fecEvento = fecEvento;
        this.nomLoginIntento = nomLoginIntento;
        this.codUsuario = codUsuario;
        this.idSesion = idSesion;
        this.codEvento = codEvento;
        this.codResultado = codResultado;
        this.txtIp = txtIp;
        this.txtDeviceId = txtDeviceId;
        this.txtUserAgent = txtUserAgent;
        this.stsDispositivoNuevo = stsDispositivoNuevo;
    }

    // Getters y Setters
    public Long getNumLog() {
        return numLog;
    }

    public void setNumLog(Long numLog) {
        this.numLog = numLog;
    }

    public LocalDateTime getFecEvento() {
        return fecEvento;
    }

    public void setFecEvento(LocalDateTime fecEvento) {
        this.fecEvento = fecEvento;
    }

    public String getNomLoginIntento() {
        return nomLoginIntento;
    }

    public void setNomLoginIntento(String nomLoginIntento) {
        this.nomLoginIntento = nomLoginIntento;
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

    public String getCodEvento() {
        return codEvento;
    }

    public void setCodEvento(String codEvento) {
        this.codEvento = codEvento;
    }

    public String getCodResultado() {
        return codResultado;
    }

    public void setCodResultado(String codResultado) {
        this.codResultado = codResultado;
    }

    public String getTxtIp() {
        return txtIp;
    }

    public void setTxtIp(String txtIp) {
        this.txtIp = txtIp;
    }

    public String getTxtDeviceId() {
        return txtDeviceId;
    }

    public void setTxtDeviceId(String txtDeviceId) {
        this.txtDeviceId = txtDeviceId;
    }

    public String getTxtUserAgent() {
        return txtUserAgent;
    }

    public void setTxtUserAgent(String txtUserAgent) {
        this.txtUserAgent = txtUserAgent;
    }

    public String getStsDispositivoNuevo() {
        return stsDispositivoNuevo;
    }

    public void setStsDispositivoNuevo(String stsDispositivoNuevo) {
        this.stsDispositivoNuevo = stsDispositivoNuevo;
    }

}
