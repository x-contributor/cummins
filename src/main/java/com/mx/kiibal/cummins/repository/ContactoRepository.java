package com.mx.kiibal.cummins.repository;

import com.mx.kiibal.cummins.domain.Contacto;

import org.springframework.data.jpa.repository.*;

import java.util.List;

/**
 * Spring Data JPA repository for the Contacto entity.
 */
@SuppressWarnings("unused")
public interface ContactoRepository extends JpaRepository<Contacto,Long> {

}
