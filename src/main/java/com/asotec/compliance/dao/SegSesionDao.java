package com.asotec.compliance.dao;

import com.asotec.compliance.entity.SegSesion;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.UUID;

@Repository
public interface SegSesionDao extends JpaRepository<SegSesion, UUID> {

    @Modifying
    @Query("UPDATE SegSesion s SET s.stsSesion = 'I', s.fecFin = current_timestamp WHERE s.codUsuario = :codUsuario AND s.stsSesion = 'A'")
    void cerrarSesionesActivasDelUsuario(@Param("codUsuario") Long codUsuario);
}
