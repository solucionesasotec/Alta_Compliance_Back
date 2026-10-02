/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.asotec.compliance.dao;

import com.asotec.compliance.entity.SegLogAcceso;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface SegLogAccesoDao extends JpaRepository<SegLogAcceso, Long> {
    // Permite registrar de manera independiente cada evento de acceso[cite: 16]
}
