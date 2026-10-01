package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.time.LocalDateTime;

@Entity
@Table(name = "seg_usuario")
public class SegUsuario {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name = "cod_institucion", nullable = false)
    private Integer codInstitucion;

    @Column(name = "nom_login", nullable = false, length = 60, unique = true)
    private String nomLogin;

    @Column(name = "nom_completo", nullable = false, length = 200)
    private String nomCompleto;

    @Column(name = "dir_correo", nullable = false, length = 150)
    private String dirCorreo;

    @Column(name = "num_celular", length = 20)
    private String numCelular;

    @Column(name = "nom_cargo", length = 100)
    private String nomCargo;

    @Column(name = "cod_agencia")
    private Integer codAgencia;

    @Column(name = "cod_area")
    private Integer codArea;

    @Column(name = "id_contraparte")
    private Long idContraparte;

    @Column(name = "txt_hash_clave", nullable = false, length = 255)
    private String txtHashClave;

    @Column(name = "fec_cambio_clave", nullable = false)
    private LocalDateTime fecCambioClave;

    @Column(name = "sts_cambio_obligatorio", nullable = false, length = 1)
    private String stsCambioObligatorio;

    @Column(name = "num_intentos_fallidos", nullable = false)
    private Integer numIntentosFallidos;

    @Column(name = "fec_bloqueo_hasta")
    private LocalDateTime fecBloqueoHasta;

    @Column(name = "sts_mfa", nullable = false, length = 1)
    private String stsMfa;

    @Column(name = "fec_ultimo_acceso")
    private LocalDateTime fecUltimoAcceso;

    @Column(name = "sts_usuario", nullable = false, length = 1)
    private String stsUsuario; // Valores: A, B, I

    @Column(name = "fec_estado", nullable = false)
    private LocalDateTime fecEstado;

    @Column(name = "txt_motivo_estado", length = 500)
    private String txtMotivoEstado;

    @Column(name = "fec_creacion", nullable = false)
    private LocalDateTime fecCreacion;

    @Column(name = "cod_usrcrea")
    private Long codUsrcrea;

    @Column(name = "cod_usrmod")
    private Long codUsrmod;

    @Column(name = "fec_usrmod", nullable = false)
    private LocalDateTime fecUsrmod;

    // Getters y Setters
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

    public String getNomLogin() {
        return nomLogin;
    }

    public void setNomLogin(String nomLogin) {
        this.nomLogin = nomLogin;
    }

    public String getNomCompleto() {
        return nomCompleto;
    }

    public void setNomCompleto(String nomCompleto) {
        this.nomCompleto = nomCompleto;
    }

    public String getDirCorreo() {
        return dirCorreo;
    }

    public void setDirCorreo(String dirCorreo) {
        this.dirCorreo = dirCorreo;
    }

    public String getNumCelular() {
        return numCelular;
    }

    public void setNumCelular(String numCelular) {
        this.numCelular = numCelular;
    }

    public String getNomCargo() {
        return nomCargo;
    }

    public void setNomCargo(String nomCargo) {
        this.nomCargo = nomCargo;
    }

    public Integer getCodAgencia() {
        return codAgencia;
    }

    public void setCodAgencia(Integer codAgencia) {
        this.codAgencia = codAgencia;
    }

    public Integer getCodArea() {
        return codArea;
    }

    public void setCodArea(Integer codArea) {
        this.codArea = codArea;
    }

    public Long getIdContraparte() {
        return idContraparte;
    }

    public void setIdContraparte(Long idContraparte) {
        this.idContraparte = idContraparte;
    }

    public String getTxtHashClave() {
        return txtHashClave;
    }

    public void setTxtHashClave(String txtHashClave) {
        this.txtHashClave = txtHashClave;
    }

    public LocalDateTime getFecCambioClave() {
        return fecCambioClave;
    }

    public void setFecCambioClave(LocalDateTime fecCambioClave) {
        this.fecCambioClave = fecCambioClave;
    }

    public String getStsCambioObligatorio() {
        return stsCambioObligatorio;
    }

    public void setStsCambioObligatorio(String stsCambioObligatorio) {
        this.stsCambioObligatorio = stsCambioObligatorio;
    }

    public Integer getNumIntentosFallidos() {
        return numIntentosFallidos;
    }

    public void setNumIntentosFallidos(Integer numIntentosFallidos) {
        this.numIntentosFallidos = numIntentosFallidos;
    }

    public LocalDateTime getFecBloqueoHasta() {
        return fecBloqueoHasta;
    }

    public void setFecBloqueoHasta(LocalDateTime fecBloqueoHasta) {
        this.fecBloqueoHasta = fecBloqueoHasta;
    }

    public String getStsMfa() {
        return stsMfa;
    }

    public void setStsMfa(String stsMfa) {
        this.stsMfa = stsMfa;
    }

    public LocalDateTime getFecUltimoAcceso() {
        return fecUltimoAcceso;
    }

    public void setFecUltimoAcceso(LocalDateTime fecUltimoAcceso) {
        this.fecUltimoAcceso = fecUltimoAcceso;
    }

    public String getStsUsuario() {
        return stsUsuario;
    }

    public void setStsUsuario(String stsUsuario) {
        this.stsUsuario = stsUsuario;
    }

    public LocalDateTime getFecEstado() {
        return fecEstado;
    }

    public void setFecEstado(LocalDateTime fecEstado) {
        this.fecEstado = fecEstado;
    }

    public String getTxtMotivoEstado() {
        return txtMotivoEstado;
    }

    public void setTxtMotivoEstado(String txtMotivoEstado) {
        this.txtMotivoEstado = txtMotivoEstado;
    }

    public LocalDateTime getFecCreacion() {
        return fecCreacion;
    }

    public void setFecCreacion(LocalDateTime fecCreacion) {
        this.fecCreacion = fecCreacion;
    }

    public Long getCodUsrcrea() {
        return codUsrcrea;
    }

    public void setCodUsrcrea(Long codUsrcrea) {
        this.codUsrcrea = codUsrcrea;
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
