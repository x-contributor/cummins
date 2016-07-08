package com.mx.kiibal.cummins.web.rest;

import com.mx.kiibal.cummins.CumminsApp;
import com.mx.kiibal.cummins.domain.ProyectoConcurso;
import com.mx.kiibal.cummins.repository.ProyectoConcursoRepository;

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

import com.mx.kiibal.cummins.domain.enumeration.EstatusProyecto;
import com.mx.kiibal.cummins.domain.enumeration.EtapaProyecto;

/**
 * Test class for the ProyectoConcursoResource REST controller.
 *
 * @see ProyectoConcursoResource
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = CumminsApp.class)
@WebAppConfiguration
@IntegrationTest
public class ProyectoConcursoResourceIntTest {


    private static final EstatusProyecto DEFAULT_ESTATUS = EstatusProyecto.ACEPTADO;
    private static final EstatusProyecto UPDATED_ESTATUS = EstatusProyecto.RECHAZADO;

    private static final EtapaProyecto DEFAULT_ETAPA = EtapaProyecto.REGISTRADO;
    private static final EtapaProyecto UPDATED_ETAPA = EtapaProyecto.PRIMER_FILTRO;

    @Inject
    private ProyectoConcursoRepository proyectoConcursoRepository;

    @Inject
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Inject
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    private MockMvc restProyectoConcursoMockMvc;

    private ProyectoConcurso proyectoConcurso;

    @PostConstruct
    public void setup() {
        MockitoAnnotations.initMocks(this);
        ProyectoConcursoResource proyectoConcursoResource = new ProyectoConcursoResource();
        ReflectionTestUtils.setField(proyectoConcursoResource, "proyectoConcursoRepository", proyectoConcursoRepository);
        this.restProyectoConcursoMockMvc = MockMvcBuilders.standaloneSetup(proyectoConcursoResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setMessageConverters(jacksonMessageConverter).build();
    }

    @Before
    public void initTest() {
        proyectoConcurso = new ProyectoConcurso();
        proyectoConcurso.setEstatus(DEFAULT_ESTATUS);
        proyectoConcurso.setEtapa(DEFAULT_ETAPA);
    }

    @Test
    @Transactional
    public void createProyectoConcurso() throws Exception {
        int databaseSizeBeforeCreate = proyectoConcursoRepository.findAll().size();

        // Create the ProyectoConcurso

        restProyectoConcursoMockMvc.perform(post("/api/proyecto-concursos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(proyectoConcurso)))
                .andExpect(status().isCreated());

        // Validate the ProyectoConcurso in the database
        List<ProyectoConcurso> proyectoConcursos = proyectoConcursoRepository.findAll();
        assertThat(proyectoConcursos).hasSize(databaseSizeBeforeCreate + 1);
        ProyectoConcurso testProyectoConcurso = proyectoConcursos.get(proyectoConcursos.size() - 1);
        assertThat(testProyectoConcurso.getEstatus()).isEqualTo(DEFAULT_ESTATUS);
        assertThat(testProyectoConcurso.getEtapa()).isEqualTo(DEFAULT_ETAPA);
    }

    @Test
    @Transactional
    public void getAllProyectoConcursos() throws Exception {
        // Initialize the database
        proyectoConcursoRepository.saveAndFlush(proyectoConcurso);

        // Get all the proyectoConcursos
        restProyectoConcursoMockMvc.perform(get("/api/proyecto-concursos?sort=id,desc"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.[*].id").value(hasItem(proyectoConcurso.getId().intValue())))
                .andExpect(jsonPath("$.[*].estatus").value(hasItem(DEFAULT_ESTATUS.toString())))
                .andExpect(jsonPath("$.[*].etapa").value(hasItem(DEFAULT_ETAPA.toString())));
    }

    @Test
    @Transactional
    public void getProyectoConcurso() throws Exception {
        // Initialize the database
        proyectoConcursoRepository.saveAndFlush(proyectoConcurso);

        // Get the proyectoConcurso
        restProyectoConcursoMockMvc.perform(get("/api/proyecto-concursos/{id}", proyectoConcurso.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON))
            .andExpect(jsonPath("$.id").value(proyectoConcurso.getId().intValue()))
            .andExpect(jsonPath("$.estatus").value(DEFAULT_ESTATUS.toString()))
            .andExpect(jsonPath("$.etapa").value(DEFAULT_ETAPA.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingProyectoConcurso() throws Exception {
        // Get the proyectoConcurso
        restProyectoConcursoMockMvc.perform(get("/api/proyecto-concursos/{id}", Long.MAX_VALUE))
                .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateProyectoConcurso() throws Exception {
        // Initialize the database
        proyectoConcursoRepository.saveAndFlush(proyectoConcurso);
        int databaseSizeBeforeUpdate = proyectoConcursoRepository.findAll().size();

        // Update the proyectoConcurso
        ProyectoConcurso updatedProyectoConcurso = new ProyectoConcurso();
        updatedProyectoConcurso.setId(proyectoConcurso.getId());
        updatedProyectoConcurso.setEstatus(UPDATED_ESTATUS);
        updatedProyectoConcurso.setEtapa(UPDATED_ETAPA);

        restProyectoConcursoMockMvc.perform(put("/api/proyecto-concursos")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(updatedProyectoConcurso)))
                .andExpect(status().isOk());

        // Validate the ProyectoConcurso in the database
        List<ProyectoConcurso> proyectoConcursos = proyectoConcursoRepository.findAll();
        assertThat(proyectoConcursos).hasSize(databaseSizeBeforeUpdate);
        ProyectoConcurso testProyectoConcurso = proyectoConcursos.get(proyectoConcursos.size() - 1);
        assertThat(testProyectoConcurso.getEstatus()).isEqualTo(UPDATED_ESTATUS);
        assertThat(testProyectoConcurso.getEtapa()).isEqualTo(UPDATED_ETAPA);
    }

    @Test
    @Transactional
    public void deleteProyectoConcurso() throws Exception {
        // Initialize the database
        proyectoConcursoRepository.saveAndFlush(proyectoConcurso);
        int databaseSizeBeforeDelete = proyectoConcursoRepository.findAll().size();

        // Get the proyectoConcurso
        restProyectoConcursoMockMvc.perform(delete("/api/proyecto-concursos/{id}", proyectoConcurso.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk());

        // Validate the database is empty
        List<ProyectoConcurso> proyectoConcursos = proyectoConcursoRepository.findAll();
        assertThat(proyectoConcursos).hasSize(databaseSizeBeforeDelete - 1);
    }
}
