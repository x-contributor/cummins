package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.ProyectoConcurso;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the ProyectoConcurso entity.
 */
@SuppressWarnings("unused")
public interface ProyectoConcursoRepository extends JpaRepository<ProyectoConcurso,Long> {

}
