package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.ParticipanteDescripcion;
import com.mx.kiibal.cummins.repository.ParticipanteDescripcionRepository;
import com.mx.kiibal.cummins.web.rest.util.HeaderUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.inject.Inject;
import java.net.URI;
import java.net.URISyntaxException;
import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing ParticipanteDescripcion.
 */
@RestController
@RequestMapping("/api")
public class ParticipanteDescripcionResource {

    private final Logger log = LoggerFactory.getLogger(ParticipanteDescripcionResource.class);
        
    @Inject
    private ParticipanteDescripcionRepository participanteDescripcionRepository;
    
    /**
     * POST  /participante-descripcions : Create a new participanteDescripcion.
     *
     * @param participanteDescripcion the participanteDescripcion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new participanteDescripcion, or with status 400 (Bad Request) if the participanteDescripcion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/participante-descripcions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParticipanteDescripcion> createParticipanteDescripcion(@RequestBody ParticipanteDescripcion participanteDescripcion) throws URISyntaxException {
        log.debug("REST request to save ParticipanteDescripcion : {}", participanteDescripcion);
        if (participanteDescripcion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("participanteDescripcion", "idexists", "A new participanteDescripcion cannot already have an ID")).body(null);
        }
        ParticipanteDescripcion result = participanteDescripcionRepository.save(participanteDescripcion);
        return ResponseEntity.created(new URI("/api/participante-descripcions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("participanteDescripcion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /participante-descripcions : Updates an existing participanteDescripcion.
     *
     * @param participanteDescripcion the participanteDescripcion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated participanteDescripcion,
     * or with status 400 (Bad Request) if the participanteDescripcion is not valid,
     * or with status 500 (Internal Server Error) if the participanteDescripcion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/participante-descripcions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParticipanteDescripcion> updateParticipanteDescripcion(@RequestBody ParticipanteDescripcion participanteDescripcion) throws URISyntaxException {
        log.debug("REST request to update ParticipanteDescripcion : {}", participanteDescripcion);
        if (participanteDescripcion.getId() == null) {
            return createParticipanteDescripcion(participanteDescripcion);
        }
        ParticipanteDescripcion result = participanteDescripcionRepository.save(participanteDescripcion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("participanteDescripcion", participanteDescripcion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /participante-descripcions : get all the participanteDescripcions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of participanteDescripcions in body
     */
    @RequestMapping(value = "/participante-descripcions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ParticipanteDescripcion> getAllParticipanteDescripcions() {
        log.debug("REST request to get all ParticipanteDescripcions");
        List<ParticipanteDescripcion> participanteDescripcions = participanteDescripcionRepository.findAll();
        return participanteDescripcions;
    }

    /**
     * GET  /participante-descripcions/:id : get the "id" participanteDescripcion.
     *
     * @param id the id of the participanteDescripcion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the participanteDescripcion, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/participante-descripcions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ParticipanteDescripcion> getParticipanteDescripcion(@PathVariable Long id) {
        log.debug("REST request to get ParticipanteDescripcion : {}", id);
        ParticipanteDescripcion participanteDescripcion = participanteDescripcionRepository.findOne(id);
        return Optional.ofNullable(participanteDescripcion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /participante-descripcions/:id : delete the "id" participanteDescripcion.
     *
     * @param id the id of the participanteDescripcion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/participante-descripcions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteParticipanteDescripcion(@PathVariable Long id) {
        log.debug("REST request to delete ParticipanteDescripcion : {}", id);
        participanteDescripcionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("participanteDescripcion", id.toString())).build();
    }

}
