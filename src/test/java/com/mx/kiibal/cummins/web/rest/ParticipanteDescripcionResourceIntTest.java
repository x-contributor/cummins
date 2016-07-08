package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.ParticipanteDescripcion;
import com.mx.kiibal.cummins.repository.ParticipanteDescripcionRepository;

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
import org.springframework.util.Base64Utils;

import javax.annotation.PostConstruct;
import javax.inject.Inject;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;


/**
 * Test class for the ParticipanteDescripcionResource REST controller.
 *
 * @see ParticipanteDescripcionResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class ParticipanteDescripcionResourceIntTest {

    private static final String DEFAULT_MISION_VISION = "AAAAA";
    private static final String UPDATED_MISION_VISION = "BBBBB";

    private static final Integer DEFAULT_ANIOS_OPERACION = 1;
    private static final Integer UPDATED_ANIOS_OPERACION = 2;
    private static final String DEFAULT_PROGRAMAS_SERVCIOS = "AAAAA";
    private static final String UPDATED_PROGRAMAS_SERVCIOS = "BBBBB";
    private static final String DEFAULT_RESULTADOS_LOGROS = "AAAAA";
    private static final String UPDATED_RESULTADOS_LOGROS = "BBBBB";

    private static final Integer DEFAULT_NUMERO_BENEFICIARIOS = 1;
    private static final Integer UPDATED_NUMERO_BENEFICIARIOS = 2;
    private static final String DEFAULT_COMENTARIOS = "AAAAA";
    private static final String UPDATED_COMENTARIOS = "BBBBB";

    private static final byte[] DEFAULT_LOGOTIPO = TestUtil.createByteArray(1, "0");
    private static final byte[] UPDATED_LOGOTIPO = TestUtil.createByteArray(2, "1");
    private static final String DEFAULT_LOGOTIPO_CONTENT_TYPE = "image/jpg";
    private static final String UPDATED_LOGOTIPO_CONTENT_TYPE = "image/png";

    @Inject
    private ParticipanteDescripcionRepository participanteDescripcionRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restParticipanteDescripcionMockMvc;

    private ParticipanteDescripcion participanteDescripcion;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ParticipanteDescripcionResource participanteDescripcionResource = new ParticipanteDescripcionResource();
        ReflectionTestUtils.setField(participanteDescripcionResource, "participanteDescripcionRepository", participanteDescripcionRepository);
        this.restParticipanteDescripcionMockMvc = MockMvcBuilders.standaloneSetup(participanteDescripcionResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        participanteDescripcion = new ParticipanteDescripcion();
        participanteDescripcion.setMisionVision(DEFAULT_MISION_VISION);
        participanteDescripcion.setAniosOperacion(DEFAULT_ANIOS_OPERACION);
        participanteDescripcion.setProgramasServcios(DEFAULT_PROGRAMAS_SERVCIOS);
        participanteDescripcion.setResultadosLogros(DEFAULT_RESULTADOS_LOGROS);
        participanteDescripcion.setNumeroBeneficiarios(DEFAULT_NUMERO_BENEFICIARIOS);
        participanteDescripcion.setComentarios(DEFAULT_COMENTARIOS);
        participanteDescripcion.setLogotipo(DEFAULT_LOGOTIPO);
        participanteDescripcion.setLogotipoContentType(DEFAULT_LOGOTIPO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void createParticipanteDescripcion() throws Exception {
        int databaseSizeBeforeCreate = participanteDescripcionRepository.findAll().size();

        // Create the ParticipanteDescripcion

        restParticipanteDescripcionMockMvc.perform(post("/api/participante-descripcions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(participanteDescripcion)))
                .andExpect(status().isCreated());

        // Validate the ParticipanteDescripcion in the database
        List<ParticipanteDescripcion> participanteDescripcions = participanteDescripcionRepository.findAll();
        assertThat(participanteDescripcions).hasSize(databaseSizeBeforeCreate + 1);
        ParticipanteDescripcion testParticipanteDescripcion = participanteDescripcions.get(participanteDescripcions.size() - 1);
        assertThat(testParticipanteDescripcion.getMisionVision()).isEqualTo(DEFAULT_MISION_VISION);
        assertThat(testParticipanteDescripcion.getAniosOperacion()).isEqualTo(DEFAULT_ANIOS_OPERACION);
        assertThat(testParticipanteDescripcion.getProgramasServcios()).isEqualTo(DEFAULT_PROGRAMAS_SERVCIOS);
        assertThat(testParticipanteDescripcion.getResultadosLogros()).isEqualTo(DEFAULT_RESULTADOS_LOGROS);
        assertThat(testParticipanteDescripcion.getNumeroBeneficiarios()).isEqualTo(DEFAULT_NUMERO_BENEFICIARIOS);
        assertThat(testParticipanteDescripcion.getComentarios()).isEqualTo(DEFAULT_COMENTARIOS);
        assertThat(testParticipanteDescripcion.getLogotipo()).isEqualTo(DEFAULT_LOGOTIPO);
        assertThat(testParticipanteDescripcion.getLogotipoContentType()).isEqualTo(DEFAULT_LOGOTIPO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void getAllParticipanteDescripcions() throws Exception {
        // Initialize the database
        participanteDescripcionRepository.saveAndFlush(participanteDescripcion);

        // Get all the participanteDescripcions
        restParticipanteDescripcionMockMvc.perform(get("/api/participante-descripcions?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(participanteDescripcion.getId().intValue())))
                .andExpect(jsonPath("$.[*].misionVision").value(hasItem(DEFAULT_MISION_VISION.toString())))
                .andExpect(jsonPath("$.[*].aniosOperacion").value(hasItem(DEFAULT_ANIOS_OPERACION)))
                .andExpect(jsonPath("$.[*].programasServcios").value(hasItem(DEFAULT_PROGRAMAS_SERVCIOS.toString())))
                .andExpect(jsonPath("$.[*].resultadosLogros").value(hasItem(DEFAULT_RESULTADOS_LOGROS.toString())))
                .andExpect(jsonPath("$.[*].numeroBeneficiarios").value(hasItem(DEFAULT_NUMERO_BENEFICIARIOS)))
                .andExpect(jsonPath("$.[*].comentarios").value(hasItem(DEFAULT_COMENTARIOS.toString())))
                .andExpect(jsonPath("$.[*].logotipoContentType").value(hasItem(DEFAULT_LOGOTIPO_CONTENT_TYPE)))
                .andExpect(jsonPath("$.[*].logotipo").value(hasItem(Base64Utils.encodeToString(DEFAULT_LOGOTIPO))));
    }

    @Test
    @Transactional
    public void getParticipanteDescripcion() throws Exception {
        // Initialize the database
        participanteDescripcionRepository.saveAndFlush(participanteDescripcion);

        // Get the participanteDescripcion
        restParticipanteDescripcionMockMvc.perform(get("/api/participante-descripcions/{id}", participanteDescripcion.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(participanteDescripcion.getId().intValue()))
            .andExpect(jsonPath("$.misionVision").value(DEFAULT_MISION_VISION.toString()))
            .andExpect(jsonPath("$.aniosOperacion").value(DEFAULT_ANIOS_OPERACION))
            .andExpect(jsonPath("$.programasServcios").value(DEFAULT_PROGRAMAS_SERVCIOS.toString()))
            .andExpect(jsonPath("$.resultadosLogros").value(DEFAULT_RESULTADOS_LOGROS.toString()))
            .andExpect(jsonPath("$.numeroBeneficiarios").value(DEFAULT_NUMERO_BENEFICIARIOS))
            .andExpect(jsonPath("$.comentarios").value(DEFAULT_COMENTARIOS.toString()))
            .andExpect(jsonPath("$.logotipoContentType").value(DEFAULT_LOGOTIPO_CONTENT_TYPE))
            .andExpect(jsonPath("$.logotipo").value(Base64Utils.encodeToString(DEFAULT_LOGOTIPO)));
    }

    @Test
    @Transactional
    public void getNonExistingParticipanteDescripcion() throws Exception {
        // Get the participanteDescripcion
        restParticipanteDescripcionMockMvc.perform(get("/api/participante-descripcions/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateParticipanteDescripcion() throws Exception {
        // Initialize the database
        participanteDescripcionRepository.saveAndFlush(participanteDescripcion);
        int databaseSizeBeforeUpdate = participanteDescripcionRepository.findAll().size();

        // Update the participanteDescripcion
        ParticipanteDescripcion updatedParticipanteDescripcion = new ParticipanteDescripcion();
        updatedParticipanteDescripcion.setId(participanteDescripcion.getId());
        updatedParticipanteDescripcion.setMisionVision(UPDATED_MISION_VISION);
        updatedParticipanteDescripcion.setAniosOperacion(UPDATED_ANIOS_OPERACION);
        updatedParticipanteDescripcion.setProgramasServcios(UPDATED_PROGRAMAS_SERVCIOS);
        updatedParticipanteDescripcion.setResultadosLogros(UPDATED_RESULTADOS_LOGROS);
        updatedParticipanteDescripcion.setNumeroBeneficiarios(UPDATED_NUMERO_BENEFICIARIOS);
        updatedParticipanteDescripcion.setComentarios(UPDATED_COMENTARIOS);
        updatedParticipanteDescripcion.setLogotipo(UPDATED_LOGOTIPO);
        updatedParticipanteDescripcion.setLogotipoContentType(UPDATED_LOGOTIPO_CONTENT_TYPE);

        restParticipanteDescripcionMockMvc.perform(put("/api/participante-descripcions")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedParticipanteDescripcion)))
                .andExpect(status().isOk());

        // Validate the ParticipanteDescripcion in the database
        List<ParticipanteDescripcion> participanteDescripcions = participanteDescripcionRepository.findAll();
        assertThat(participanteDescripcions).hasSize(databaseSizeBeforeUpdate);
        ParticipanteDescripcion testParticipanteDescripcion = participanteDescripcions.get(participanteDescripcions.size() - 1);
        assertThat(testParticipanteDescripcion.getMisionVision()).isEqualTo(UPDATED_MISION_VISION);
        assertThat(testParticipanteDescripcion.getAniosOperacion()).isEqualTo(UPDATED_ANIOS_OPERACION);
        assertThat(testParticipanteDescripcion.getProgramasServcios()).isEqualTo(UPDATED_PROGRAMAS_SERVCIOS);
        assertThat(testParticipanteDescripcion.getResultadosLogros()).isEqualTo(UPDATED_RESULTADOS_LOGROS);
        assertThat(testParticipanteDescripcion.getNumeroBeneficiarios()).isEqualTo(UPDATED_NUMERO_BENEFICIARIOS);
        assertThat(testParticipanteDescripcion.getComentarios()).isEqualTo(UPDATED_COMENTARIOS);
        assertThat(testParticipanteDescripcion.getLogotipo()).isEqualTo(UPDATED_LOGOTIPO);
        assertThat(testParticipanteDescripcion.getLogotipoContentType()).isEqualTo(UPDATED_LOGOTIPO_CONTENT_TYPE);
    }

    @Test
    @Transactional
    public void deleteParticipanteDescripcion() throws Exception {
        // Initialize the database
        participanteDescripcionRepository.saveAndFlush(participanteDescripcion);
        int databaseSizeBeforeDelete = participanteDescripcionRepository.findAll().size();

        // Get the participanteDescripcion
        restParticipanteDescripcionMockMvc.perform(delete("/api/participante-descripcions/{id}", participanteDescripcion.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ParticipanteDescripcion> participanteDescripcions = participanteDescripcionRepository.findAll();
        assertThat(participanteDescripcions).hasSize(databaseSizeBeforeDelete - 1);
    }
}
