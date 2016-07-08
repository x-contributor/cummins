package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.Respuesta;
import com.mx.kiibal.cummins.repository.RespuestaRepository;
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
 * REST controller for managing Respuesta.
 */
@RestController
@RequestMapping("/api")
public class RespuestaResource {

    private final Logger log = LoggerFactory.getLogger(RespuestaResource.class);
        
    @Inject
    private RespuestaRepository respuestaRepository;
    
    /**
     * POST  /respuestas : Create a new respuesta.
     *
     * @param respuesta the respuesta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new respuesta, or with status 400 (Bad Request) if the respuesta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/respuestas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Respuesta> createRespuesta(@RequestBody Respuesta respuesta) throws URISyntaxException {
        log.debug("REST request to save Respuesta : {}", respuesta);
        if (respuesta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("respuesta", "idexists", "A new respuesta cannot already have an ID")).body(null);
        }
        Respuesta result = respuestaRepository.save(respuesta);
        return ResponseEntity.created(new URI("/api/respuestas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("respuesta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /respuestas : Updates an existing respuesta.
     *
     * @param respuesta the respuesta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated respuesta,
     * or with status 400 (Bad Request) if the respuesta is not valid,
     * or with status 500 (Internal Server Error) if the respuesta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/respuestas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Respuesta> updateRespuesta(@RequestBody Respuesta respuesta) throws URISyntaxException {
        log.debug("REST request to update Respuesta : {}", respuesta);
        if (respuesta.getId() == null) {
            return createRespuesta(respuesta);
        }
        Respuesta result = respuestaRepository.save(respuesta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("respuesta", respuesta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /respuestas : get all the respuestas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of respuestas in body
     */
    @RequestMapping(value = "/respuestas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Respuesta> getAllRespuestas() {
        log.debug("REST request to get all Respuestas");
        List<Respuesta> respuestas = respuestaRepository.findAll();
        return respuestas;
    }

    /**
     * GET  /respuestas/:id : get the "id" respuesta.
     *
     * @param id the id of the respuesta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the respuesta, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/respuestas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Respuesta> getRespuesta(@PathVariable Long id) {
        log.debug("REST request to get Respuesta : {}", id);
        Respuesta respuesta = respuestaRepository.findOne(id);
        return Optional.ofNullable(respuesta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /respuestas/:id : delete the "id" respuesta.
     *
     * @param id the id of the respuesta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/respuestas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteRespuesta(@PathVariable Long id) {
        log.debug("REST request to delete Respuesta : {}", id);
        respuestaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("respuesta", id.toString())).build();
    }

}
