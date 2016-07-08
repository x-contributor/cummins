package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.PreguntaSeccion;
import com.mx.kiibal.cummins.repository.PreguntaSeccionRepository;
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
 * REST controller for managing PreguntaSeccion.
 */
@RestController
@RequestMapping("/api")
public class PreguntaSeccionResource {

    private final Logger log = LoggerFactory.getLogger(PreguntaSeccionResource.class);
        
    @Inject
    private PreguntaSeccionRepository preguntaSeccionRepository;
    
    /**
     * POST  /pregunta-seccions : Create a new preguntaSeccion.
     *
     * @param preguntaSeccion the preguntaSeccion to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preguntaSeccion, or with status 400 (Bad Request) if the preguntaSeccion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pregunta-seccions",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreguntaSeccion> createPreguntaSeccion(@RequestBody PreguntaSeccion preguntaSeccion) throws URISyntaxException {
        log.debug("REST request to save PreguntaSeccion : {}", preguntaSeccion);
        if (preguntaSeccion.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("preguntaSeccion", "idexists", "A new preguntaSeccion cannot already have an ID")).body(null);
        }
        PreguntaSeccion result = preguntaSeccionRepository.save(preguntaSeccion);
        return ResponseEntity.created(new URI("/api/pregunta-seccions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("preguntaSeccion", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pregunta-seccions : Updates an existing preguntaSeccion.
     *
     * @param preguntaSeccion the preguntaSeccion to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preguntaSeccion,
     * or with status 400 (Bad Request) if the preguntaSeccion is not valid,
     * or with status 500 (Internal Server Error) if the preguntaSeccion couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/pregunta-seccions",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreguntaSeccion> updatePreguntaSeccion(@RequestBody PreguntaSeccion preguntaSeccion) throws URISyntaxException {
        log.debug("REST request to update PreguntaSeccion : {}", preguntaSeccion);
        if (preguntaSeccion.getId() == null) {
            return createPreguntaSeccion(preguntaSeccion);
        }
        PreguntaSeccion result = preguntaSeccionRepository.save(preguntaSeccion);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("preguntaSeccion", preguntaSeccion.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pregunta-seccions : get all the preguntaSeccions.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preguntaSeccions in body
     */
    @RequestMapping(value = "/pregunta-seccions",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<PreguntaSeccion> getAllPreguntaSeccions() {
        log.debug("REST request to get all PreguntaSeccions");
        List<PreguntaSeccion> preguntaSeccions = preguntaSeccionRepository.findAll();
        return preguntaSeccions;
    }

    /**
     * GET  /pregunta-seccions/:id : get the "id" preguntaSeccion.
     *
     * @param id the id of the preguntaSeccion to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preguntaSeccion, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/pregunta-seccions/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<PreguntaSeccion> getPreguntaSeccion(@PathVariable Long id) {
        log.debug("REST request to get PreguntaSeccion : {}", id);
        PreguntaSeccion preguntaSeccion = preguntaSeccionRepository.findOne(id);
        return Optional.ofNullable(preguntaSeccion)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /pregunta-seccions/:id : delete the "id" preguntaSeccion.
     *
     * @param id the id of the preguntaSeccion to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/pregunta-seccions/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePreguntaSeccion(@PathVariable Long id) {
        log.debug("REST request to delete PreguntaSeccion : {}", id);
        preguntaSeccionRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("preguntaSeccion", id.toString())).build();
    }

}
