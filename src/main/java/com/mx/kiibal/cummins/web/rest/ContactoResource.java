package com.mx.kiibal.cummins.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.mx.kiibal.cummins.domain.Contacto;
import com.mx.kiibal.cummins.repository.ContactoRepository;
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
 * REST controller for managing Contacto.
 */
@RestController
@RequestMapping("/api")
public class ContactoResource {

    private final Logger log = LoggerFactory.getLogger(ContactoResource.class);
        
    @Inject
    private ContactoRepository contactoRepository;
    
    /**
     * POST  /contactos : Create a new contacto.
     *
     * @param contacto the contacto to create
     * @return the ResponseEntity with status 201 (Created) and with body the new contacto, or with status 400 (Bad Request) if the contacto has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/contactos",
        method = RequestMethod.POST,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacto> createContacto(@RequestBody Contacto contacto) throws URISyntaxException {
        log.debug("REST request to save Contacto : {}", contacto);
        if (contacto.getId() != null) {
            return ResponseEntity.badRequest().headers(HeaderUtil.createFailureAlert("contacto", "idexists", "A new contacto cannot already have an ID")).body(null);
        }
        Contacto result = contactoRepository.save(contacto);
        return ResponseEntity.created(new URI("/api/contactos/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert("contacto", result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /contactos : Updates an existing contacto.
     *
     * @param contacto the contacto to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated contacto,
     * or with status 400 (Bad Request) if the contacto is not valid,
     * or with status 500 (Internal Server Error) if the contacto couldnt be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @RequestMapping(value = "/contactos",
        method = RequestMethod.PUT,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacto> updateContacto(@RequestBody Contacto contacto) throws URISyntaxException {
        log.debug("REST request to update Contacto : {}", contacto);
        if (contacto.getId() == null) {
            return createContacto(contacto);
        }
        Contacto result = contactoRepository.save(contacto);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert("contacto", contacto.getId().toString()))
            .body(result);
    }

    /**
     * GET  /contactos : get all the contactos.
     *
     * @return the ResponseEntity with status 200 (OK) and the list of contactos in body
     */
    @RequestMapping(value = "/contactos",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public List<Contacto> getAllContactos() {
        log.debug("REST request to get all Contactos");
        List<Contacto> contactos = contactoRepository.findAll();
        return contactos;
    }

    /**
     * GET  /contactos/:id : get the "id" contacto.
     *
     * @param id the id of the contacto to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the contacto, or with status 404 (Not Found)
     */
    @RequestMapping(value = "/contactos/{id}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacto> getContacto(@PathVariable Long id) {
        log.debug("REST request to get Contacto : {}", id);
        Contacto contacto = contactoRepository.findOne(id);
        return Optional.ofNullable(contacto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

    /**
     * DELETE  /contactos/:id : delete the "id" contacto.
     *
     * @param id the id of the contacto to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @RequestMapping(value = "/contactos/{id}",
        method = RequestMethod.DELETE,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Void> deleteContacto(@PathVariable Long id) {
        log.debug("REST request to delete Contacto : {}", id);
        contactoRepository.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert("contacto", id.toString())).build();
    }
    
    @RequestMapping(value = "/contactos/email/{email}",
        method = RequestMethod.GET,
        produces = MediaType.APPLICATION_JSON_VALUE)
    @Timed
    public ResponseEntity<Contacto> getContactoByEmail(@PathVariable String email) {
        log.debug("REST request to getContactoByEmail: {}", email);
        Contacto contacto = contactoRepository.findByEmail(email);
        return Optional.ofNullable(contacto)
            .map(result -> new ResponseEntity<>(
                result,
                HttpStatus.OK))
            .orElse(new ResponseEntity<>(HttpStatus.NOT_FOUND));
    }

}
