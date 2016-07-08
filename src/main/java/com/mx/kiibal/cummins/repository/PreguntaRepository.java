package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.Pregunta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Pregunta entity.
 */
@SuppressWarnings("unused")
public interface PreguntaRepository extends JpaRepository<Pregunta,Long> {

}
