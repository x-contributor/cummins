package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.Respuesta;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Respuesta entity.
 */
@SuppressWarnings("unused")
public interface RespuestaRepository extends JpaRepository<Respuesta,Long> {

}
