package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.VoluntarioVisitador;
import com.mx.kiibal.cummins.repository.VoluntarioVisitadorRepository;

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
 * Test class for the VoluntarioVisitadorResource REST controller.
 *
 * @see VoluntarioVisitadorResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class VoluntarioVisitadorResourceIntTest {

    private static final String DEFAULT_NOMBRE = "AAAAA";
    private static final String UPDATED_NOMBRE = "BBBBB";
    private static final String DEFAULT_TELEFONO = "AAAAA";
    private static final String UPDATED_TELEFONO = "BBBBB";

    @Inject
    private VoluntarioVisitadorRepository voluntarioVisitadorRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restVoluntarioVisitadorMockMvc;

    private VoluntarioVisitador voluntarioVisitador;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        VoluntarioVisitadorResource voluntarioVisitadorResource = new VoluntarioVisitadorResource();
        ReflectionTestUtils.setField(voluntarioVisitadorResource, "voluntarioVisitadorRepository", voluntarioVisitadorRepository);
        this.restVoluntarioVisitadorMockMvc = MockMvcBuilders.standaloneSetup(voluntarioVisitadorResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        voluntarioVisitador = new VoluntarioVisitador();
        voluntarioVisitador.setNombre(DEFAULT_NOMBRE);
        voluntarioVisitador.setTelefono(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void createVoluntarioVisitador() throws Exception {
        int databaseSizeBeforeCreate = voluntarioVisitadorRepository.findAll().size();

        // Create the VoluntarioVisitador

        restVoluntarioVisitadorMockMvc.perform(post("/api/voluntario-visitadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(voluntarioVisitador)))
                .andExpect(status().isCreated());

        // Validate the VoluntarioVisitador in the database
        List<VoluntarioVisitador> voluntarioVisitadors = voluntarioVisitadorRepository.findAll();
        assertThat(voluntarioVisitadors).hasSize(databaseSizeBeforeCreate + 1);
        VoluntarioVisitador testVoluntarioVisitador = voluntarioVisitadors.get(voluntarioVisitadors.size() - 1);
        assertThat(testVoluntarioVisitador.getNombre()).isEqualTo(DEFAULT_NOMBRE);
        assertThat(testVoluntarioVisitador.getTelefono()).isEqualTo(DEFAULT_TELEFONO);
    }

    @Test
    @Transactional
    public void getAllVoluntarioVisitadors() throws Exception {
        // Initialize the database
        voluntarioVisitadorRepository.saveAndFlush(voluntarioVisitador);

        // Get all the voluntarioVisitadors
        restVoluntarioVisitadorMockMvc.perform(get("/api/voluntario-visitadors?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(voluntarioVisitador.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombre").value(hasItem(DEFAULT_NOMBRE.toString())))
                .andExpect(jsonPath("$.[*].telefono").value(hasItem(DEFAULT_TELEFONO.toString())));
    }

    @Test
    @Transactional
    public void getVoluntarioVisitador() throws Exception {
        // Initialize the database
        voluntarioVisitadorRepository.saveAndFlush(voluntarioVisitador);

        // Get the voluntarioVisitador
        restVoluntarioVisitadorMockMvc.perform(get("/api/voluntario-visitadors/{id}", voluntarioVisitador.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(voluntarioVisitador.getId().intValue()))
            .andExpect(jsonPath("$.nombre").value(DEFAULT_NOMBRE.toString()))
            .andExpect(jsonPath("$.telefono").value(DEFAULT_TELEFONO.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingVoluntarioVisitador() throws Exception {
        // Get the voluntarioVisitador
        restVoluntarioVisitadorMockMvc.perform(get("/api/voluntario-visitadors/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateVoluntarioVisitador() throws Exception {
        // Initialize the database
        voluntarioVisitadorRepository.saveAndFlush(voluntarioVisitador);
        int databaseSizeBeforeUpdate = voluntarioVisitadorRepository.findAll().size();

        // Update the voluntarioVisitador
        VoluntarioVisitador updatedVoluntarioVisitador = new VoluntarioVisitador();
        updatedVoluntarioVisitador.setId(voluntarioVisitador.getId());
        updatedVoluntarioVisitador.setNombre(UPDATED_NOMBRE);
        updatedVoluntarioVisitador.setTelefono(UPDATED_TELEFONO);

        restVoluntarioVisitadorMockMvc.perform(put("/api/voluntario-visitadors")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedVoluntarioVisitador)))
                .andExpect(status().isOk());

        // Validate the VoluntarioVisitador in the database
        List<VoluntarioVisitador> voluntarioVisitadors = voluntarioVisitadorRepository.findAll();
        assertThat(voluntarioVisitadors).hasSize(databaseSizeBeforeUpdate);
        VoluntarioVisitador testVoluntarioVisitador = voluntarioVisitadors.get(voluntarioVisitadors.size() - 1);
        assertThat(testVoluntarioVisitador.getNombre()).isEqualTo(UPDATED_NOMBRE);
        assertThat(testVoluntarioVisitador.getTelefono()).isEqualTo(UPDATED_TELEFONO);
    }

    @Test
    @Transactional
    public void deleteVoluntarioVisitador() throws Exception {
        // Initialize the database
        voluntarioVisitadorRepository.saveAndFlush(voluntarioVisitador);
        int databaseSizeBeforeDelete = voluntarioVisitadorRepository.findAll().size();

        // Get the voluntarioVisitador
        restVoluntarioVisitadorMockMvc.perform(delete("/api/voluntario-visitadors/{id}", voluntarioVisitador.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<VoluntarioVisitador> voluntarioVisitadors = voluntarioVisitadorRepository.findAll();
        assertThat(voluntarioVisitadors).hasSize(databaseSizeBeforeDelete - 1);
    }
}
