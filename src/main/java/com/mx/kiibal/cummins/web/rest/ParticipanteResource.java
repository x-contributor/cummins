package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.Participante;
import com.mx.kiibal.cummins.repository.ParticipanteRepository;
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
import java.util.stream.Collectors;
import java.util.stream.StreamSupport;

/**
 * REST controller for managing Participante.
 */
@RestController
@RequestMapping("/api")
public class ParticipanteResource {

    private final Logger log = LoggerFactory.getLogger(ParticipanteResource.class);
        
    @Inject
    private ParticipanteRepository participanteRepository;
    
    /**
     * POST  /participantes : Create a new participante.
     *
     * @param participante the participante to create
     * @return the ResponseEntity with status 201 (Created) and with body the new participante, or with status 400 (Bad Request) if the participante has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/participantes",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Participante> createParticipante(@RequestBody Participante participante) throws URISyntaxException {
        log.debug("REST request to save Participante : {}", participante);
        if (participante.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("participante", "idexists", "A new participante cannot already have an ID")).body(null);
        }
        Participante result = participanteRepository.save(participante);
        return ResponseEntity.created(new URI("/api/participantes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("participante", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /participantes : Updates an existing participante.
     *
     * @param participante the participante to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated participante,
     * or with status 400 (Bad Request) if the participante is not valid,
     * or with status 500 (Internal Server Error) if the participante couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/participantes",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Participante> updateParticipante(@RequestBody Participante participante) throws URISyntaxException {
        log.debug("REST request to update Participante : {}", participante);
        if (participante.getId() == null) {
            return createParticipante(participante);
        }
        Participante result = participanteRepository.save(participante);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("participante", participante.getId().toString()))
            .body(result);
    }

    /**
     * GET  /participantes : get all the participantes.
     *
     * @param filter the filter of the request
     * @return the ResponseEntity with status 200 (OK) and the list of participantes in body
     */
    @RequestMapping(value = "/participantes",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Participante> getAllParticipantes(@RequestParam(required = false) String filter) {
        if ("contacto-is-null".equals(filter)) {
            log.debug("REST request to get all Participantes where contacto is null");
            return StreamSupport
                .stream(participanteRepository.findAll().spliterator(), false)
                .filter(participante -> participante.getContacto() == null)
                .collect(Collectors.toList());
        }
        if ("participantedescripcion-is-null".equals(filter)) {
            log.debug("REST request to get all Participantes where participanteDescripcion is null");
            return StreamSupport
                .stream(participanteRepository.findAll().spliterator(), false)
                .filter(participante -> participante.getParticipanteDescripcion() == null)
                .collect(Collectors.toList());
        }
        log.debug("REST request to get all Participantes");
        List<Participante> participantes = participanteRepository.findAll();
        return participantes;
    }

    /**
     * GET  /participantes/:id : get the "id" participante.
     *
     * @param id the id of the participante to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the participante, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/participantes/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Participante> getParticipante(@PathVariable Long id) {
        log.debug("REST request to get Participante : {}", id);
        Participante participante = participanteRepository.findOne(id);
        return Optional.ofNullable(participante)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /participantes/:id : delete the "id" participante.
     *
     * @param id the id of the participante to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/participantes/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteParticipante(@PathVariable Long id) {
        log.debug("REST request to delete Participante : {}", id);
        participanteRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("participante", id.toString())).build();
    }

}
