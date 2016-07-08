package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.ParticipanteDescripcion;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ParticipanteDescripcion entity.
 */
@SuppressWarnings("unused")
public interface ParticipanteDescripcionRepository extends JpaRepository<ParticipanteDescripcion,Long> {

}
