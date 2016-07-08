package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.Participante;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Participante entity.
 */
@SuppressWarnings("unused")
public interface ParticipanteRepository extends JpaRepository<Participante,Long> {

}
