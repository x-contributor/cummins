package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.Proyecto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Proyecto entity.
 */
@SuppressWarnings("unused")
public interface ProyectoRepository extends JpaRepository<Proyecto,Long> {

    @Query("SELECT p "
            + " FROM User u, VoluntarioVisitador v, Proyecto p"
            + " WHERE  u.firstName = v.nombre AND v = p.visitador AND u.login = ?1")
    List<Proyecto> obtenerProyectosVisitador(String login);
}
