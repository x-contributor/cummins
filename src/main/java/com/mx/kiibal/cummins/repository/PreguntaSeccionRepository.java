package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.PreguntaSeccion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the PreguntaSeccion entity.
 */
@SuppressWarnings("unused")
public interface PreguntaSeccionRepository extends JpaRepository<PreguntaSeccion,Long> {

}
