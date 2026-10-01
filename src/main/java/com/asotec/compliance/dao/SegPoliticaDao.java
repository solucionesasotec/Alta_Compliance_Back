/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package com.asotec.compliance.dao;

import com.asotec.compliance.entity.SegPolitica;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface SegPoliticaDao extends JpaRepository<SegPolitica, Integer> {

    // Consulta la política por código de institución según la especificación
    Optional<SegPolitica> findByCodInstitucion(Integer codInstitucion);
}
