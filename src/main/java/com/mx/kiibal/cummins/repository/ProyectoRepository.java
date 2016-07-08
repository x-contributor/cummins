package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.Proyecto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proyecto entity.
 */
@SuppressWarnings("unused")
public interface ProyectoRepository extends JpaRepository<Proyecto,Long> {

}
