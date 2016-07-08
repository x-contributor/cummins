package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.Pregunta;
import com.mx.kiibal.cummins.repository.PreguntaRepository;

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

import com.mx.kiibal.cummins.domain.enumeration.TipoPregunta;

/**
 * Test class for the PreguntaResource REST controller.
 *
 * @see PreguntaResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class PreguntaResourceIntTest {


    private static final TipoPregunta DEFAULT_TIPO_PREGUNTA = TipoPregunta.SI_NO;
    private static final TipoPregunta UPDATED_TIPO_PREGUNTA = TipoPregunta.CANTIDAD;
    private static final String DEFAULT_PREGUNTA = "AAAAA";
    private static final String UPDATED_PREGUNTA = "BBBBB";
    private static final String DEFAULT_AYUDA = "AAAAA";
    private static final String UPDATED_AYUDA = "BBBBB";

    private static final Integer DEFAULT_MAX = 1;
    private static final Integer UPDATED_MAX = 2;

    private static final Integer DEFAULT_MIN = 1;
    private static final Integer UPDATED_MIN = 2;

    @Inject
    private PreguntaRepository preguntaRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPreguntaMockMvc;

    private Pregunta pregunta;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PreguntaResource preguntaResource = new PreguntaResource();
        ReflectionTestUtils.setField(preguntaResource, "preguntaRepository", preguntaRepository);
        this.restPreguntaMockMvc = MockMvcBuilders.standaloneSetup(preguntaResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        pregunta = new Pregunta();
        pregunta.setTipoPregunta(DEFAULT_TIPO_PREGUNTA);
        pregunta.setPregunta(DEFAULT_PREGUNTA);
        pregunta.setAyuda(DEFAULT_AYUDA);
        pregunta.setMax(DEFAULT_MAX);
        pregunta.setMin(DEFAULT_MIN);
    }

    @Test
    @Transactional
    public void createPregunta() throws Exception {
        int databaseSizeBeforeCreate = preguntaRepository.findAll().size();

        // Create the Pregunta

        restPreguntaMockMvc.perform(post("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(pregunta)))
                .andExpect(status().isCreated());

        // Validate the Pregunta in the database
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(databaseSizeBeforeCreate + 1);
        Pregunta testPregunta = preguntas.get(preguntas.size() - 1);
        assertThat(testPregunta.getTipoPregunta()).isEqualTo(DEFAULT_TIPO_PREGUNTA);
        assertThat(testPregunta.getPregunta()).isEqualTo(DEFAULT_PREGUNTA);
        assertThat(testPregunta.getAyuda()).isEqualTo(DEFAULT_AYUDA);
        assertThat(testPregunta.getMax()).isEqualTo(DEFAULT_MAX);
        assertThat(testPregunta.getMin()).isEqualTo(DEFAULT_MIN);
    }

    @Test
    @Transactional
    public void getAllPreguntas() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);

        // Get all the preguntas
        restPreguntaMockMvc.perform(get("/api/preguntas?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(pregunta.getId().intValue())))
                .andExpect(jsonPath("$.[*].tipoPregunta").value(hasItem(DEFAULT_TIPO_PREGUNTA.toString())))
                .andExpect(jsonPath("$.[*].pregunta").value(hasItem(DEFAULT_PREGUNTA.toString())))
                .andExpect(jsonPath("$.[*].ayuda").value(hasItem(DEFAULT_AYUDA.toString())))
                .andExpect(jsonPath("$.[*].max").value(hasItem(DEFAULT_MAX)))
                .andExpect(jsonPath("$.[*].min").value(hasItem(DEFAULT_MIN)));
    }

    @Test
    @Transactional
    public void getPregunta() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);

        // Get the pregunta
        restPreguntaMockMvc.perform(get("/api/preguntas/{id}", pregunta.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(pregunta.getId().intValue()))
            .andExpect(jsonPath("$.tipoPregunta").value(DEFAULT_TIPO_PREGUNTA.toString()))
            .andExpect(jsonPath("$.pregunta").value(DEFAULT_PREGUNTA.toString()))
            .andExpect(jsonPath("$.ayuda").value(DEFAULT_AYUDA.toString()))
            .andExpect(jsonPath("$.max").value(DEFAULT_MAX))
            .andExpect(jsonPath("$.min").value(DEFAULT_MIN));
    }

    @Test
    @Transactional
    public void getNonExistingPregunta() throws Exception {
        // Get the pregunta
        restPreguntaMockMvc.perform(get("/api/preguntas/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePregunta() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);
        int databaseSizeBeforeUpdate = preguntaRepository.findAll().size();

        // Update the pregunta
        Pregunta updatedPregunta = new Pregunta();
        updatedPregunta.setId(pregunta.getId());
        updatedPregunta.setTipoPregunta(UPDATED_TIPO_PREGUNTA);
        updatedPregunta.setPregunta(UPDATED_PREGUNTA);
        updatedPregunta.setAyuda(UPDATED_AYUDA);
        updatedPregunta.setMax(UPDATED_MAX);
        updatedPregunta.setMin(UPDATED_MIN);

        restPreguntaMockMvc.perform(put("/api/preguntas")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPregunta)))
                .andExpect(status().isOk());

        // Validate the Pregunta in the database
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(databaseSizeBeforeUpdate);
        Pregunta testPregunta = preguntas.get(preguntas.size() - 1);
        assertThat(testPregunta.getTipoPregunta()).isEqualTo(UPDATED_TIPO_PREGUNTA);
        assertThat(testPregunta.getPregunta()).isEqualTo(UPDATED_PREGUNTA);
        assertThat(testPregunta.getAyuda()).isEqualTo(UPDATED_AYUDA);
        assertThat(testPregunta.getMax()).isEqualTo(UPDATED_MAX);
        assertThat(testPregunta.getMin()).isEqualTo(UPDATED_MIN);
    }

    @Test
    @Transactional
    public void deletePregunta() throws Exception {
        // Initialize the database
        preguntaRepository.saveAndFlush(pregunta);
        int databaseSizeBeforeDelete = preguntaRepository.findAll().size();

        // Get the pregunta
        restPreguntaMockMvc.perform(delete("/api/preguntas/{id}", pregunta.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<Pregunta> preguntas = preguntaRepository.findAll();
        assertThat(preguntas).hasSize(databaseSizeBeforeDelete - 1);
    }
}
