package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.Proyecto;
import com.mx.kiibal.cummins.domain.ProyectoConcurso;
import com.mx.kiibal.cummins.domain.enumeration.EstatusProyecto;
import com.mx.kiibal.cummins.repository.ParticipanteRepository;
import com.mx.kiibal.cummins.repository.ProyectoConcursoRepository;
import com.mx.kiibal.cummins.repository.ProyectoRepository;
import com.mx.kiibal.cummins.service.MailService;
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
 * REST controller for managing ProyectoConcurso.
 */
@RestController
@RequestMapping("/api")
public class ProyectoConcursoResource {

    private final Logger log = LoggerFactory.getLogger(ProyectoConcursoResource.class);
        
    @Inject
    private ProyectoConcursoRepository proyectoConcursoRepository;
    
    @Inject
    private MailService mailService;
    
    @Inject 
    private ProyectoRepository proyectoRepository;
    
    /**
     * POST  /proyecto-concursos : Create a new proyectoConcurso.
     *
     * @param proyectoConcurso the proyectoConcurso to create
     * @return the ResponseEntity with status 201 (Created) and with body the new proyectoConcurso, or with status 400 (Bad Request) if the proyectoConcurso has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/proyecto-concursos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProyectoConcurso> createProyectoConcurso(@RequestBody ProyectoConcurso proyectoConcurso) throws URISyntaxException {
        log.debug("REST request to save ProyectoConcurso : {}", proyectoConcurso);
        if (proyectoConcurso.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("proyectoConcurso", "idexists", "A new proyectoConcurso cannot already have an ID")).body(null);
        }
        ProyectoConcurso result = proyectoConcursoRepository.save(proyectoConcurso);
        log.debug(result.getProyecto().toString());
        if (proyectoConcurso.getEstatus() == EstatusProyecto.ACEPTADO) {
            Proyecto proeyctoId = proyectoRepository.findOne(proyectoConcurso.getProyecto().getId());
            String email = proeyctoId.getParticipante().getContacto().getEmail();
            try {
                mailService.sendEmail(email, "Concurso Filantropía 2016", "Su proyecto ha sido ACEPTADO", false, false);
            } catch (Exception e) {
                log.error("error al enviar el correo", e);
            }
            
        }
        return ResponseEntity.created(new URI("/api/proyecto-concursos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("proyectoConcurso", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /proyecto-concursos : Updates an existing proyectoConcurso.
     *
     * @param proyectoConcurso the proyectoConcurso to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated proyectoConcurso,
     * or with status 400 (Bad Request) if the proyectoConcurso is not valid,
     * or with status 500 (Internal Server Error) if the proyectoConcurso couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/proyecto-concursos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProyectoConcurso> updateProyectoConcurso(@RequestBody ProyectoConcurso proyectoConcurso) throws URISyntaxException {
        log.debug("REST request to update ProyectoConcurso : {}", proyectoConcurso);
        if (proyectoConcurso.getId() == null) {
            return createProyectoConcurso(proyectoConcurso);
        }
        ProyectoConcurso result = proyectoConcursoRepository.save(proyectoConcurso);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("proyectoConcurso", proyectoConcurso.getId().toString()))
            .body(result);
    }

    /**
     * GET  /proyecto-concursos : get all the proyectoConcursos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of proyectoConcursos in body
     */
    @RequestMapping(value = "/proyecto-concursos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<ProyectoConcurso> getAllProyectoConcursos() {
        log.debug("REST request to get all ProyectoConcursos");
        List<ProyectoConcurso> proyectoConcursos = proyectoConcursoRepository.findAll();
        return proyectoConcursos;
    }

    /**
     * GET  /proyecto-concursos/:id : get the "id" proyectoConcurso.
     *
     * @param id the id of the proyectoConcurso to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the proyectoConcurso, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/proyecto-concursos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<ProyectoConcurso> getProyectoConcurso(@PathVariable Long id) {
        log.debug("REST request to get ProyectoConcurso : {}", id);
        ProyectoConcurso proyectoConcurso = proyectoConcursoRepository.findOne(id);
        return Optional.ofNullable(proyectoConcurso)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /proyecto-concursos/:id : delete the "id" proyectoConcurso.
     *
     * @param id the id of the proyectoConcurso to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/proyecto-concursos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteProyectoConcurso(@PathVariable Long id) {
        log.debug("REST request to delete ProyectoConcurso : {}", id);
        proyectoConcursoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("proyectoConcurso", id.toString())).build();
    }

}
