package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.VoluntarioVisitador;
import com.mx.kiibal.cummins.repository.VoluntarioVisitadorRepository;
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
 * REST controller for managing VoluntarioVisitador.
 */
@RestController
@RequestMapping("/api")
public class VoluntarioVisitadorResource {

    private final Logger log = LoggerFactory.getLogger(VoluntarioVisitadorResource.class);
        
    @Inject
    private VoluntarioVisitadorRepository voluntarioVisitadorRepository;
    
    /**
     * POST  /voluntario-visitadors : Create a new voluntarioVisitador.
     *
     * @param voluntarioVisitador the voluntarioVisitador to create
     * @return the ResponseEntity with status 201 (Created) and with body the new voluntarioVisitador, or with status 400 (Bad Request) if the voluntarioVisitador has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/voluntario-visitadors",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VoluntarioVisitador> createVoluntarioVisitador(@RequestBody VoluntarioVisitador voluntarioVisitador) throws URISyntaxException {
        log.debug("REST request to save VoluntarioVisitador : {}", voluntarioVisitador);
        if (voluntarioVisitador.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("voluntarioVisitador", "idexists", "A new voluntarioVisitador cannot already have an ID")).body(null);
        }
        VoluntarioVisitador result = voluntarioVisitadorRepository.save(voluntarioVisitador);
        return ResponseEntity.created(new URI("/api/voluntario-visitadors/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("voluntarioVisitador", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /voluntario-visitadors : Updates an existing voluntarioVisitador.
     *
     * @param voluntarioVisitador the voluntarioVisitador to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated voluntarioVisitador,
     * or with status 400 (Bad Request) if the voluntarioVisitador is not valid,
     * or with status 500 (Internal Server Error) if the voluntarioVisitador couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/voluntario-visitadors",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VoluntarioVisitador> updateVoluntarioVisitador(@RequestBody VoluntarioVisitador voluntarioVisitador) throws URISyntaxException {
        log.debug("REST request to update VoluntarioVisitador : {}", voluntarioVisitador);
        if (voluntarioVisitador.getId() == null) {
            return createVoluntarioVisitador(voluntarioVisitador);
        }
        VoluntarioVisitador result = voluntarioVisitadorRepository.save(voluntarioVisitador);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("voluntarioVisitador", voluntarioVisitador.getId().toString()))
            .body(result);
    }

    /**
     * GET  /voluntario-visitadors : get all the voluntarioVisitadors.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of voluntarioVisitadors in body
     */
    @RequestMapping(value = "/voluntario-visitadors",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<VoluntarioVisitador> getAllVoluntarioVisitadors() {
        log.debug("REST request to get all VoluntarioVisitadors");
        List<VoluntarioVisitador> voluntarioVisitadors = voluntarioVisitadorRepository.findAll();
        return voluntarioVisitadors;
    }

    /**
     * GET  /voluntario-visitadors/:id : get the "id" voluntarioVisitador.
     *
     * @param id the id of the voluntarioVisitador to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the voluntarioVisitador, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/voluntario-visitadors/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<VoluntarioVisitador> getVoluntarioVisitador(@PathVariable Long id) {
        log.debug("REST request to get VoluntarioVisitador : {}", id);
        VoluntarioVisitador voluntarioVisitador = voluntarioVisitadorRepository.findOne(id);
        return Optional.ofNullable(voluntarioVisitador)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /voluntario-visitadors/:id : delete the "id" voluntarioVisitador.
     *
     * @param id the id of the voluntarioVisitador to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/voluntario-visitadors/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteVoluntarioVisitador(@PathVariable Long id) {
        log.debug("REST request to delete VoluntarioVisitador : {}", id);
        voluntarioVisitadorRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("voluntarioVisitador", id.toString())).build();
    }

}
