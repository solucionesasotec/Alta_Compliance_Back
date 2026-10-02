package com.asotec.compliance.dao;

import com.asotec.compliance.entity.SegUsuario;
import java.util.Optional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface SegUsuarioDao extends JpaRepository<SegUsuario, Long> {

    // Buscar usuario ignorando mayúsculas/minúsculas[cite: 6]
    @Query("SELECT u FROM SegUsuario u WHERE LOWER(u.nomLogin) = LOWER(:login)")
    SegUsuario findByNomLoginIgnoreCase(@Param("login") String login);

    // Sumar intento fallido y actualizar bloqueo de forma atómica[cite: 7]
    @Modifying
    @Query(value = "UPDATE seg_usuario SET num_intentos_fallidos = CASE WHEN num_intentos_fallidos + 1 >= :intentosMax THEN 0 ELSE num_intentos_fallidos + 1 END, fec_bloqueo_hasta = CASE WHEN num_intentos_fallidos + 1 >= :intentosMax THEN now() + :minutosBloqueo * interval '1 minute' ELSE fec_bloqueo_hasta END WHERE cod_usuario = :codUsuario", nativeQuery = true)
    void registrarIntentoFallido(@Param("codUsuario") Long codUsuario, @Param("intentosMax") int intentosMax, @Param("minutosBloqueo") int minutosBloqueo);

    @Modifying
    @Query("UPDATE SegUsuario u SET u.numIntentosFallidos = 0, u.fecBloqueoHasta = null WHERE u.codUsuario = :codUsuario")
    void reiniciarIntentosFallidos(@Param("codUsuario") Long codUsuario);
}
