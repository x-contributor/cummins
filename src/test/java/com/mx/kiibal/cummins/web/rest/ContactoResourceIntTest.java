package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.Contacto;
import com.mx.kiibal.cummins.repository.ContactoRepository;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import static org.hamcrest.Matchers.hasItem;
import org.mockito.MockitoAnnotations;
import org.springframework.boot.test.IntegrationTest;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.util.ReflectionTestUtils;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ContactoResource REST controller.
 *
 * @see ContactoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class ContactoResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";
    private static final String DEFAULT_EMAIL = "AAAAA";
    private static final String UPDATED_EMAIL = "BBBBB";
    private static final String DEFAULT_CARGO = "AAAAA";
    private static final String UPDATED_CARGO = "BBBBB";
    private static final String DEFAULT_DIRECCION = "AAAAA";
    private static final String UPDATED_DIRECCION = "BBBBB";
    private static final String DEFAULT_TELEFONO = "AAAAA";
    private static final String UPDATED_TELEFONO = "BBBBB";

    @Inject
    private ContactoRepository contactoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restContactoMockMvc;

    private Contacto contacto;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ContactoResource contactoResource = new ContactoResource();
        ReflectionTestUtils.setField(contactoResource, "contactoRepository", contactoRepository);
        this.restContactoMockMvc = MockMvcBuilders.standaloneSetup(contactoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        contacto = new Contacto();
        contacto.setNombre(DEFAULT_NOMBRE);
        contacto.setEmail(DEFAULT_EMAIL);
        contacto.setCargo(DEFAULT_CARGO);
        contacto.setDireccion(DEFAULT_DIRECCION);
        contacto.setTelefono(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createContacto() throws Exception {
        int databaseSizeBeforeCreate = contactoRepository.findAll().size();

        // Create the Contacto

        restContactoMockMvc.perform(post("/api/contactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(contacto)))
                .andExpect(status().isCreated());

        // Validate the Contacto in the database
        List<Contacto> contactos = contactoRepository.findAll();
        assertThat(contactos).hasSize(databaseSizeBeforeCreate + 1);
        Contacto testContacto = contactos.get(contactos.size() - 1);
        assertThat(testContacto.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testContacto.getEmail()).isEqualTo(DEFAULT_EMAIL);
        assertThat(testContacto.getCargo()).isEqualTo(DEFAULT_CARGO);
        assertThat(testContacto.getDireccion()).isEqualTo(DEFAULT_DIRECCION);
        assertThat(testContacto.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllContactos() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get all the contactos
        restContactoMockMvc.perform(get("/api/contactos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(contacto.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].email").value(hasItem(DEFAULT_EMAIL.toString())))
                .andExpect(jsonPath("$.[*].cargo").value(hasItem(DEFAULT_CARGO.toString())))
                .andExpect(jsonPath("$.[*].direccion").value(hasItem(DEFAULT_DIRECCION.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }

    @Test
    @Transactional
    public void getContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);

        // Get the contacto
        restContactoMockMvc.perform(get("/api/contactos/{id}", contacto.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(contacto.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.email").value(DEFAULT_EMAIL.toString()))
            .andExpect(jsonPath("$.cargo").value(DEFAULT_CARGO.toString()))
            .andExpect(jsonPath("$.direccion").value(DEFAULT_DIRECCION.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingContacto() throws Exception {
        // Get the contacto
        restContactoMockMvc.perform(get("/api/contactos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);
        int databaseSizeBeforeUpdate = contactoRepository.findAll().size();

        // Update the contacto
        Contacto updatedContacto = new Contacto();
        updatedContacto.setId(contacto.getId());
        updatedContacto.setNombre(UPDATED_NOMBRE);
        updatedContacto.setEmail(UPDATED_EMAIL);
        updatedContacto.setCargo(UPDATED_CARGO);
        updatedContacto.setDireccion(UPDATED_DIRECCION);
        updatedContacto.setTelefono(UPDATED_TELEFONO);

        restContactoMockMvc.perform(put("/api/contactos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedContacto)))
                .andExpect(status().isOk());

        // Validate the Contacto in the database
        List<Contacto> contactos = contactoRepository.findAll();
        assertThat(contactos).hasSize(databaseSizeBeforeUpdate);
        Contacto testContacto = contactos.get(contactos.size() - 1);
        assertThat(testContacto.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testContacto.getEmail()).isEqualTo(UPDATED_EMAIL);
        assertThat(testContacto.getCargo()).isEqualTo(UPDATED_CARGO);
        assertThat(testContacto.getDireccion()).isEqualTo(UPDATED_DIRECCION);
        assertThat(testContacto.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void deleteContacto() throws Exception {
        // Initialize the database
        contactoRepository.saveAndFlush(contacto);
        int databaseSizeBeforeDelete = contactoRepository.findAll().size();

        // Get the contacto
        restContactoMockMvc.perform(delete("/api/contactos/{id}", contacto.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Contacto> contactos = contactoRepository.findAll();
        assertThat(contactos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
