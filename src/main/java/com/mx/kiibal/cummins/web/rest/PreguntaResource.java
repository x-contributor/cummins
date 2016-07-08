package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.Pregunta;
import com.mx.kiibal.cummins.repository.PreguntaRepository;
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
 * REST controller for managing Pregunta.
 */
@RestController
@RequestMapping("/api")
public class PreguntaResource {

    private final Logger log = LoggerFactory.getLogger(PreguntaResource.class);
        
    @Inject
    private PreguntaRepository preguntaRepository;
    
    /**
     * POST  /preguntas : Create a new pregunta.
     *
     * @param pregunta the pregunta to create
     * @return the ResponseEntity with status 201 (Created) and with body the new pregunta, or with status 400 (Bad Request) if the pregunta has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/preguntas",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pregunta> createPregunta(@RequestBody Pregunta pregunta) throws URISyntaxException {
        log.debug("REST request to save Pregunta : {}", pregunta);
        if (pregunta.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("pregunta", "idexists", "A new pregunta cannot already have an ID")).body(null);
        }
        Pregunta result = preguntaRepository.save(pregunta);
        return ResponseEntity.created(new URI("/api/preguntas/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("pregunta", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /preguntas : Updates an existing pregunta.
     *
     * @param pregunta the pregunta to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated pregunta,
     * or with status 400 (Bad Request) if the pregunta is not valid,
     * or with status 500 (Internal Server Error) if the pregunta couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/preguntas",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pregunta> updatePregunta(@RequestBody Pregunta pregunta) throws URISyntaxException {
        log.debug("REST request to update Pregunta : {}", pregunta);
        if (pregunta.getId() == null) {
            return createPregunta(pregunta);
        }
        Pregunta result = preguntaRepository.save(pregunta);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("pregunta", pregunta.getId().toString()))
            .body(result);
    }

    /**
     * GET  /preguntas : get all the preguntas.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of preguntas in body
     */
    @RequestMapping(value = "/preguntas",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Pregunta> getAllPreguntas() {
        log.debug("REST request to get all Preguntas");
        List<Pregunta> preguntas = preguntaRepository.findAll();
        return preguntas;
    }

    /**
     * GET  /preguntas/:id : get the "id" pregunta.
     *
     * @param id the id of the pregunta to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the pregunta, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/preguntas/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Pregunta> getPregunta(@PathVariable Long id) {
        log.debug("REST request to get Pregunta : {}", id);
        Pregunta pregunta = preguntaRepository.findOne(id);
        return Optional.ofNullable(pregunta)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /preguntas/:id : delete the "id" pregunta.
     *
     * @param id the id of the pregunta to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/preguntas/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deletePregunta(@PathVariable Long id) {
        log.debug("REST request to delete Pregunta : {}", id);
        preguntaRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("pregunta", id.toString())).build();
    }

}
