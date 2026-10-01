package com.asotec.compliance.entity;

import jakarta.persistence.*;
import java.io.Serializable;
import java.util.Objects;

@Embeddable
class SegRolPermisoId implements Serializable {

    @Column(name = "cod_rol", length = 30)
    private String codRol;

    @Column(name = "cod_permiso", length = 60)
    private String codPermiso;

    public SegRolPermisoId() {
    }

    public SegRolPermisoId(String codRol, String codPermiso) {
        this.codRol = codRol;
        this.codPermiso = codPermiso;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (!(o instanceof SegRolPermisoId that)) {
            return false;
        }
        return Objects.equals(codRol, that.codRol) && Objects.equals(codPermiso, that.codPermiso);
    }

    @Override
    public int hashCode() {
        return Objects.hash(codRol, codPermiso);
    }

    // Getters y Setters
    public String getCodRol() {
        return codRol;
    }

    public void setCodRol(String codRol) {
        this.codRol = codRol;
    }

    public String getCodPermiso() {
        return codPermiso;
    }

    public void setCodPermiso(String codPermiso) {
        this.codPermiso = codPermiso;
    }

}

@Entity
@Table(name = "seg_rol_permiso")
public class SegRolPermiso {

    @EmbeddedId
    private SegRolPermisoId id;

    // Getters y Setters
    public SegRolPermisoId getId() {
        return id;
    }

    public void setId(SegRolPermisoId id) {
        this.id = id;
    }

}
