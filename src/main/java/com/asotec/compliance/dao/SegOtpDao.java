package com.asotec.compliance.dao;

import com.asotec.compliance.entity.SegOtp;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import java.util.Optional;

@Repository
public interface SegOtpDao extends JpaRepository<SegOtp, Long> {

    @Modifying
    @Query("UPDATE SegOtp o SET o.stsOtp = 'X' WHERE o.codUsuario = :codUsuario AND o.stsOtp = 'P'")
    void anularOtpsPendientes(@Param("codUsuario") Long codUsuario);

    Optional<SegOtp> findByNumOtpAndCodUsuario(Long numOtp, Long codUsuario);
}
