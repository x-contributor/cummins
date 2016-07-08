package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.VoluntarioVisitador;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the VoluntarioVisitador entity.
 */
@SuppressWarnings("unused")
public interface VoluntarioVisitadorRepository extends JpaRepository<VoluntarioVisitador,Long> {

}
