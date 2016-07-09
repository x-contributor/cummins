package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.PreguntaSeccion;
import com.mx.kiibal.cummins.repository.PreguntaSeccionRepository;

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

import com.mx.kiibal.cummins.domain.enumeration.TipoProyecto;

/**
 * Test class for the PreguntaSeccionResource REST controller.
 *
 * @see PreguntaSeccionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class PreguntaSeccionResourceIntTest {

    private static final String DEFAULT_PREGUNTA_TITULO = "AAAAA";
    private static final String UPDATED_PREGUNTA_TITULO = "BBBBB";
    private static final String DEFAULT_PREGUNTA_INSTRUCCIONES = "AAAAA";
    private static final String UPDATED_PREGUNTA_INSTRUCCIONES = "BBBBB";
    private static final String DEFAULT_PREGUNTA_AYUDA = "AAAAA";
    private static final String UPDATED_PREGUNTA_AYUDA = "BBBBB";

    private static final Integer DEFAULT_CONSECUTIVO_SECCION = 1;
    private static final Integer UPDATED_CONSECUTIVO_SECCION = 2;

    private static final TipoProyecto DEFAULT_TIPO_PROYECTO = TipoProyecto.TODOS;
    private static final TipoProyecto UPDATED_TIPO_PROYECTO = TipoProyecto.EXISTENTE;

    private static final Integer DEFAULT_MINIMO_RESPUESTA = 1;
    private static final Integer UPDATED_MINIMO_RESPUESTA = 2;

    private static final Integer DEFAULT_MAXIMO_RESPUESTA = 1;
    private static final Integer UPDATED_MAXIMO_RESPUESTA = 2;

    private static final Integer DEFAULT_SUMA_TOTAL = 1;
    private static final Integer UPDATED_SUMA_TOTAL = 2;

    @Inject
    private PreguntaSeccionRepository preguntaSeccionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restPreguntaSeccionMockMvc;

    private PreguntaSeccion preguntaSeccion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        PreguntaSeccionResource preguntaSeccionResource = new PreguntaSeccionResource();
        ReflectionTestUtils.setField(preguntaSeccionResource, "preguntaSeccionRepository", preguntaSeccionRepository);
        this.restPreguntaSeccionMockMvc = MockMvcBuilders.standaloneSetup(preguntaSeccionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        preguntaSeccion = new PreguntaSeccion();
        preguntaSeccion.setPreguntaTitulo(DEFAULT_PREGUNTA_TITULO);
        preguntaSeccion.setPreguntaInstrucciones(DEFAULT_PREGUNTA_INSTRUCCIONES);
        preguntaSeccion.setPreguntaAyuda(DEFAULT_PREGUNTA_AYUDA);
        preguntaSeccion.setConsecutivoSeccion(DEFAULT_CONSECUTIVO_SECCION);
        preguntaSeccion.setTipoProyecto(DEFAULT_TIPO_PROYECTO);
        preguntaSeccion.setMinimoRespuesta(DEFAULT_MINIMO_RESPUESTA);
        preguntaSeccion.setMaximoRespuesta(DEFAULT_MAXIMO_RESPUESTA);
        preguntaSeccion.setSumaTotal(DEFAULT_SUMA_TOTAL);
    }

    @Test
    @Transactional
    public void createPreguntaSeccion() throws Exception {
        int databaseSizeBeforeCreate = preguntaSeccionRepository.findAll().size();

        // Create the PreguntaSeccion

        restPreguntaSeccionMockMvc.perform(post("/api/pregunta-seccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(preguntaSeccion)))
                .andExpect(status().isCreated());

        // Validate the PreguntaSeccion in the database
        List<PreguntaSeccion> preguntaSeccions = preguntaSeccionRepository.findAll();
        assertThat(preguntaSeccions).hasSize(databaseSizeBeforeCreate + 1);
        PreguntaSeccion testPreguntaSeccion = preguntaSeccions.get(preguntaSeccions.size() - 1);
        assertThat(testPreguntaSeccion.getPreguntaTitulo()).isEqualTo(DEFAULT_PREGUNTA_TITULO);
        assertThat(testPreguntaSeccion.getPreguntaInstrucciones()).isEqualTo(DEFAULT_PREGUNTA_INSTRUCCIONES);
        assertThat(testPreguntaSeccion.getPreguntaAyuda()).isEqualTo(DEFAULT_PREGUNTA_AYUDA);
        assertThat(testPreguntaSeccion.getConsecutivoSeccion()).isEqualTo(DEFAULT_CONSECUTIVO_SECCION);
        assertThat(testPreguntaSeccion.getTipoProyecto()).isEqualTo(DEFAULT_TIPO_PROYECTO);
        assertThat(testPreguntaSeccion.getMinimoRespuesta()).isEqualTo(DEFAULT_MINIMO_RESPUESTA);
        assertThat(testPreguntaSeccion.getMaximoRespuesta()).isEqualTo(DEFAULT_MAXIMO_RESPUESTA);
        assertThat(testPreguntaSeccion.getSumaTotal()).isEqualTo(DEFAULT_SUMA_TOTAL);
    }

    @Test
    @Transactional
    public void getAllPreguntaSeccions() throws Exception {
        // Initialize the database
        preguntaSeccionRepository.saveAndFlush(preguntaSeccion);

        // Get all the preguntaSeccions
        restPreguntaSeccionMockMvc.perform(get("/api/pregunta-seccions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(preguntaSeccion.getId().intValue())))
                .andExpect(jsonPath("$.[*].preguntaTitulo").value(hasItem(DEFAULT_PREGUNTA_TITULO.toString())))
                .andExpect(jsonPath("$.[*].preguntaInstrucciones").value(hasItem(DEFAULT_PREGUNTA_INSTRUCCIONES.toString())))
                .andExpect(jsonPath("$.[*].preguntaAyuda").value(hasItem(DEFAULT_PREGUNTA_AYUDA.toString())))
                .andExpect(jsonPath("$.[*].consecutivoSeccion").value(hasItem(DEFAULT_CONSECUTIVO_SECCION)))
                .andExpect(jsonPath("$.[*].tipoProyecto").value(hasItem(DEFAULT_TIPO_PROYECTO.toString())))
                .andExpect(jsonPath("$.[*].minimoRespuesta").value(hasItem(DEFAULT_MINIMO_RESPUESTA)))
                .andExpect(jsonPath("$.[*].maximoRespuesta").value(hasItem(DEFAULT_MAXIMO_RESPUESTA)))
                .andExpect(jsonPath("$.[*].sumaTotal").value(hasItem(DEFAULT_SUMA_TOTAL)));
    }

    @Test
    @Transactional
    public void getPreguntaSeccion() throws Exception {
        // Initialize the database
        preguntaSeccionRepository.saveAndFlush(preguntaSeccion);

        // Get the preguntaSeccion
        restPreguntaSeccionMockMvc.perform(get("/api/pregunta-seccions/{id}", preguntaSeccion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(preguntaSeccion.getId().intValue()))
            .andExpect(jsonPath("$.preguntaTitulo").value(DEFAULT_PREGUNTA_TITULO.toString()))
            .andExpect(jsonPath("$.preguntaInstrucciones").value(DEFAULT_PREGUNTA_INSTRUCCIONES.toString()))
            .andExpect(jsonPath("$.preguntaAyuda").value(DEFAULT_PREGUNTA_AYUDA.toString()))
            .andExpect(jsonPath("$.consecutivoSeccion").value(DEFAULT_CONSECUTIVO_SECCION))
            .andExpect(jsonPath("$.tipoProyecto").value(DEFAULT_TIPO_PROYECTO.toString()))
            .andExpect(jsonPath("$.minimoRespuesta").value(DEFAULT_MINIMO_RESPUESTA))
            .andExpect(jsonPath("$.maximoRespuesta").value(DEFAULT_MAXIMO_RESPUESTA))
            .andExpect(jsonPath("$.sumaTotal").value(DEFAULT_SUMA_TOTAL));
    }

    @Test
    @Transactional
    public void getNonExistingPreguntaSeccion() throws Exception {
        // Get the preguntaSeccion
        restPreguntaSeccionMockMvc.perform(get("/api/pregunta-seccions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreguntaSeccion() throws Exception {
        // Initialize the database
        preguntaSeccionRepository.saveAndFlush(preguntaSeccion);
        int databaseSizeBeforeUpdate = preguntaSeccionRepository.findAll().size();

        // Update the preguntaSeccion
        PreguntaSeccion updatedPreguntaSeccion = new PreguntaSeccion();
        updatedPreguntaSeccion.setId(preguntaSeccion.getId());
        updatedPreguntaSeccion.setPreguntaTitulo(UPDATED_PREGUNTA_TITULO);
        updatedPreguntaSeccion.setPreguntaInstrucciones(UPDATED_PREGUNTA_INSTRUCCIONES);
        updatedPreguntaSeccion.setPreguntaAyuda(UPDATED_PREGUNTA_AYUDA);
        updatedPreguntaSeccion.setConsecutivoSeccion(UPDATED_CONSECUTIVO_SECCION);
        updatedPreguntaSeccion.setTipoProyecto(UPDATED_TIPO_PROYECTO);
        updatedPreguntaSeccion.setMinimoRespuesta(UPDATED_MINIMO_RESPUESTA);
        updatedPreguntaSeccion.setMaximoRespuesta(UPDATED_MAXIMO_RESPUESTA);
        updatedPreguntaSeccion.setSumaTotal(UPDATED_SUMA_TOTAL);

        restPreguntaSeccionMockMvc.perform(put("/api/pregunta-seccions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedPreguntaSeccion)))
                .andExpect(status().isOk());

        // Validate the PreguntaSeccion in the database
        List<PreguntaSeccion> preguntaSeccions = preguntaSeccionRepository.findAll();
        assertThat(preguntaSeccions).hasSize(databaseSizeBeforeUpdate);
        PreguntaSeccion testPreguntaSeccion = preguntaSeccions.get(preguntaSeccions.size() - 1);
        assertThat(testPreguntaSeccion.getPreguntaTitulo()).isEqualTo(UPDATED_PREGUNTA_TITULO);
        assertThat(testPreguntaSeccion.getPreguntaInstrucciones()).isEqualTo(UPDATED_PREGUNTA_INSTRUCCIONES);
        assertThat(testPreguntaSeccion.getPreguntaAyuda()).isEqualTo(UPDATED_PREGUNTA_AYUDA);
        assertThat(testPreguntaSeccion.getConsecutivoSeccion()).isEqualTo(UPDATED_CONSECUTIVO_SECCION);
        assertThat(testPreguntaSeccion.getTipoProyecto()).isEqualTo(UPDATED_TIPO_PROYECTO);
        assertThat(testPreguntaSeccion.getMinimoRespuesta()).isEqualTo(UPDATED_MINIMO_RESPUESTA);
        assertThat(testPreguntaSeccion.getMaximoRespuesta()).isEqualTo(UPDATED_MAXIMO_RESPUESTA);
        assertThat(testPreguntaSeccion.getSumaTotal()).isEqualTo(UPDATED_SUMA_TOTAL);
    }

    @Test
    @Transactional
    public void deletePreguntaSeccion() throws Exception {
        // Initialize the database
        preguntaSeccionRepository.saveAndFlush(preguntaSeccion);
        int databaseSizeBeforeDelete = preguntaSeccionRepository.findAll().size();

        // Get the preguntaSeccion
        restPreguntaSeccionMockMvc.perform(delete("/api/pregunta-seccions/{id}", preguntaSeccion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<PreguntaSeccion> preguntaSeccions = preguntaSeccionRepository.findAll();
        assertThat(preguntaSeccions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
