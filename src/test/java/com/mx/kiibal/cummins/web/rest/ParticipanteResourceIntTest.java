package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.Participante;
import com.mx.kiibal.cummins.repository.ParticipanteRepository;

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
 * Test class for the ParticipanteResource REST controller.
 *
 * @see ParticipanteResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class ParticipanteResourceIntTest {

    private static final String DEFAULT_NOMBRE_AC = "AAAAA";
    private static final String UPDATED_NOMBRE_AC = "BBBBB";

    @Inject
    private ParticipanteRepository participanteRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restParticipanteMockMvc;

    private Participante participante;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParticipanteResource participanteResource = new ParticipanteResource();
        ReflectionTestUtils.setField(participanteResource, "participanteRepository", participanteRepository);
        this.restParticipanteMockMvc = MockMvcBuilders.standaloneSetup(participanteResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        participante = new Participante();
        participante.setNombreAc(DEFAULT_NOMBRE_AC);
    }

    @Test
    @Transactional
    public void createParticipante() throws Exception {
        int databaseSizeBeforeCreate = participanteRepository.findAll().size();

        // Create the Participante

        restParticipanteMockMvc.perform(post("/api/participantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(participante)))
                .andExpect(status().isCreated());

        // Validate the Participante in the database
        List<Participante> participantes = participanteRepository.findAll();
        assertThat(participantes).hasSize(databaseSizeBeforeCreate + 1);
        Participante testParticipante = participantes.get(participantes.size() - 1);
        assertThat(testParticipante.getNombreAc()).isEqualTo(DEFAULT_NOMBRE_AC);
    }

    @Test
    @Transactional
    public void getAllParticipantes() throws Exception {
        // Initialize the database
        participanteRepository.saveAndFlush(participante);

        // Get all the participantes
        restParticipanteMockMvc.perform(get("/api/participantes?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(participante.getId().intValue())))
                .andExpect(jsonPath("$.[*].nombreAc").value(hasItem(DEFAULT_NOMBRE_AC.toString())));
    }

    @Test
    @Transactional
    public void getParticipante() throws Exception {
        // Initialize the database
        participanteRepository.saveAndFlush(participante);

        // Get the participante
        restParticipanteMockMvc.perform(get("/api/participantes/{id}", participante.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(participante.getId().intValue()))
            .andExpect(jsonPath("$.nombreAc").value(DEFAULT_NOMBRE_AC.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingParticipante() throws Exception {
        // Get the participante
        restParticipanteMockMvc.perform(get("/api/participantes/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipante() throws Exception {
        // Initialize the database
        participanteRepository.saveAndFlush(participante);
        int databaseSizeBeforeUpdate = participanteRepository.findAll().size();

        // Update the participante
        Participante updatedParticipante = new Participante();
        updatedParticipante.setId(participante.getId());
        updatedParticipante.setNombreAc(UPDATED_NOMBRE_AC);

        restParticipanteMockMvc.perform(put("/api/participantes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedParticipante)))
                .andExpect(status().isOk());

        // Validate the Participante in the database
        List<Participante> participantes = participanteRepository.findAll();
        assertThat(participantes).hasSize(databaseSizeBeforeUpdate);
        Participante testParticipante = participantes.get(participantes.size() - 1);
        assertThat(testParticipante.getNombreAc()).isEqualTo(UPDATED_NOMBRE_AC);
    }

    @Test
    @Transactional
    public void deleteParticipante() throws Exception {
        // Initialize the database
        participanteRepository.saveAndFlush(participante);
        int databaseSizeBeforeDelete = participanteRepository.findAll().size();

        // Get the participante
        restParticipanteMockMvc.perform(delete("/api/participantes/{id}", participante.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Participante> participantes = participanteRepository.findAll();
        assertThat(participantes).hasSize(databaseSizeBeforeDelete - 1);
    }
}
