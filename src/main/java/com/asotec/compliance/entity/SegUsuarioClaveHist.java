package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.time.LocalDateTime;
import java.util.Objects;

@Embeddable
class SegUsuarioClaveHistId implements Serializable {

    @Column(name = "cod_usuario")
    private Long codUsuario;

    @Column(name = "num_secuencia")
    private Integer numSecuencia;

    public SegUsuarioClaveHistId() {
    }

    public SegUsuarioClaveHistId(Long codUsuario, Integer numSecuencia) {
        this.codUsuario = codUsuario;
        this.numSecuencia = numSecuencia;
    }

    // equals and hashCode son obligatorios para llaves compuestas
    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegUsuarioClaveHistId that)) {
            return false;
        }
        return Objects.equals(codUsuario, that.codUsuario) && Objects.equals(numSecuencia, that.numSecuencia);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codUsuario, numSecuencia);
    }

    // Getters y Setters
    public Long getCodUsuario() {
        return codUsuario;
    }

    public void setCodUsuario(Long codUsuario) {
        this.codUsuario = codUsuario;
    }

    public Integer getNumSecuencia() {
        return numSecuencia;
    }

    public void setNumSecuencia(Integer numSecuencia) {
        this.numSecuencia = numSecuencia;
    }

}

@Entity
@Table(name = "seg_usuario_clave_hist")
public class SegUsuarioClaveHist {

    @EmbeddedId
    private SegUsuarioClaveHistId id;

    @Column(name = "txt_hash_clave", nullable = false, length = 255)
    private String txtHashClave;

    @Column(name = "fec_registro", nullable = false)
    private LocalDateTime fecRegistro;

    // Getters y Setters
    public SegUsuarioClaveHistId getId() {
        return id;
    }

    public void setId(SegUsuarioClaveHistId id) {
        this.id = id;
    }

    public String getTxtHashClave() {
        return txtHashClave;
    }

    public void setTxtHashClave(String txtHashClave) {
        this.txtHashClave = txtHashClave;
    }

    public LocalDateTime getFecRegistro() {
        return fecRegistro;
    }

    public void setFecRegistro(LocalDateTime fecRegistro) {
        this.fecRegistro = fecRegistro;
    }

}
