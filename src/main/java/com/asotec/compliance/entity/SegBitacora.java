package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "seg_bitacora")
public class SegBitacora {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "num_bitacora")
    private Long numBitacora;

    @Column(name = "fec_evento", nullable = false)
    private LocalDateTime fecEvento;

    @Column(name = "cod_institucion", nullable = false)
    private Integer codInstitucion;

    @Column(name = "cod_usuario", nullable = false)
    private Long codUsuario;

    @Column(name = "id_sesion", nullable = false)
    private UUID idSesion;

    @Column(name = "txt_ip", nullable = false, length = 45)
    private String txtIp;

    @Column(name = "txt_device_id", length = 128)
    private String txtDeviceId;

    @Column(name = "cod_modulo", nullable = false, length = 10)
    private String codModulo;

    @Column(name = "cod_accion", nullable = false, length = 20)
    private String codAccion;

    @Column(name = "cod_permiso", length = 60)
    private String codPermiso;

    @Column(name = "txt_entidad", nullable = false, length = 60)
    private String txtEntidad;

    @Column(name = "txt_id_entidad", length = 60)
    private String txtIdEntidad;

    @Lob
    @Column(name = "txt_datos_antes", columnDefinition = "text")
    private String txtDatosAntes;

    @Lob
    @Column(name = "txt_datos_despues", columnDefinition = "text")
    private String txtDatosDespues;

    @Column(name = "txt_observacion", length = 500)
    private String txtObservacion;

    // Getters y Setters
    public Long getNumBitacora() {
        return numBitacora;
    }

    public void setNumBitacora(Long numBitacora) {
        this.numBitacora = numBitacora;
    }

    public LocalDateTime getFecEvento() {
        return fecEvento;
    }

    public void setFecEvento(LocalDateTime fecEvento) {
        this.fecEvento = fecEvento;
    }

    public Integer getCodInstitucion() {
        return codInstitucion;
    }

    public void setCodInstitucion(Integer codInstitucion) {
        this.codInstitucion = codInstitucion;
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

    public String getCodModulo() {
        return codModulo;
    }

    public void setCodModulo(String codModulo) {
        this.codModulo = codModulo;
    }

    public String getCodAccion() {
        return codAccion;
    }

    public void setCodAccion(String codAccion) {
        this.codAccion = codAccion;
    }

    public String getCodPermiso() {
        return codPermiso;
    }

    public void setCodPermiso(String codPermiso) {
        this.codPermiso = codPermiso;
    }

    public String getTxtEntidad() {
        return txtEntidad;
    }

    public void setTxtEntidad(String txtEntidad) {
        this.txtEntidad = txtEntidad;
    }

    public String getTxtIdEntidad() {
        return txtIdEntidad;
    }

    public void setTxtIdEntidad(String txtIdEntidad) {
        this.txtIdEntidad = txtIdEntidad;
    }

    public String getTxtDatosAntes() {
        return txtDatosAntes;
    }

    public void setTxtDatosAntes(String txtDatosAntes) {
        this.txtDatosAntes = txtDatosAntes;
    }

    public String getTxtDatosDespues() {
        return txtDatosDespues;
    }

    public void setTxtDatosDespues(String txtDatosDespues) {
        this.txtDatosDespues = txtDatosDespues;
    }

    public String getTxtObservacion() {
        return txtObservacion;
    }

    public void setTxtObservacion(String txtObservacion) {
        this.txtObservacion = txtObservacion;
    }

}
