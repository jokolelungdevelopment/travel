package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.Request;
import com.jokolelung.travel.repository.RequestRepository;
import com.jokolelung.travel.service.RequestService;
import com.jokolelung.travel.service.dto.RequestDTO;
import com.jokolelung.travel.service.mapper.RequestMapper;
import com.jokolelung.travel.web.rest.errors.ExceptionTranslator;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.MockitoAnnotations;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.http.MediaType;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.test.context.junit4.SpringRunner;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.jokolelung.travel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jokolelung.travel.domain.enumeration.Status;
/**
 * Test class for the RequestResource REST controller.
 *
 * @see RequestResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class RequestResourceIntTest {

    private static final Instant DEFAULT_REQUEST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_REQUEST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Long DEFAULT_VIEWED = 1L;
    private static final Long UPDATED_VIEWED = 2L;

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final Status UPDATED_STATUS = Status.REQUSTED;

    private static final Long DEFAULT_TRAVELERID = 1L;
    private static final Long UPDATED_TRAVELERID = 2L;

    @Autowired
    private RequestRepository requestRepository;

    @Autowired
    private RequestMapper requestMapper;

    @Autowired
    private RequestService requestService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restRequestMockMvc;

    private Request request;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final RequestResource requestResource = new RequestResource(requestService);
        this.restRequestMockMvc = MockMvcBuilders.standaloneSetup(requestResource)
            .setCustomArgumentResolvers(pageableArgumentResolver)
            .setControllerAdvice(exceptionTranslator)
            .setConversionService(createFormattingConversionService())
            .setMessageConverters(jacksonMessageConverter).build();
    }

    /**
     * Create an entity for this test.
     *
     * This is a static method, as tests for other entities might also need it,
     * if they test an entity which requires the current entity.
     */
    public static Request createEntity(EntityManager em) {
        Request request = new Request()
            .requestDate(DEFAULT_REQUEST_DATE)
            .viewed(DEFAULT_VIEWED)
            .status(DEFAULT_STATUS)
            .travelerid(DEFAULT_TRAVELERID);
        return request;
    }

    @Before
    public void initTest() {
        request = createEntity(em);
    }

    @Test
    @Transactional
    public void createRequest() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);
        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate + 1);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getRequestDate()).isEqualTo(DEFAULT_REQUEST_DATE);
        assertThat(testRequest.getViewed()).isEqualTo(DEFAULT_VIEWED);
        assertThat(testRequest.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testRequest.getTravelerid()).isEqualTo(DEFAULT_TRAVELERID);
    }

    @Test
    @Transactional
    public void createRequestWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = requestRepository.findAll().size();

        // Create the Request with an existing ID
        request.setId(1L);
        RequestDTO requestDTO = requestMapper.toDto(request);

        // An entity with an existing ID cannot be created, so this API call must fail
        restRequestMockMvc.perform(post("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllRequests() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get all the requestList
        restRequestMockMvc.perform(get("/api/requests?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(request.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestDate").value(hasItem(DEFAULT_REQUEST_DATE.toString())))
            .andExpect(jsonPath("$.[*].viewed").value(hasItem(DEFAULT_VIEWED.intValue())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].travelerid").value(hasItem(DEFAULT_TRAVELERID.intValue())));
    }

    @Test
    @Transactional
    public void getRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);

        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", request.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(request.getId().intValue()))
            .andExpect(jsonPath("$.requestDate").value(DEFAULT_REQUEST_DATE.toString()))
            .andExpect(jsonPath("$.viewed").value(DEFAULT_VIEWED.intValue()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.travelerid").value(DEFAULT_TRAVELERID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingRequest() throws Exception {
        // Get the request
        restRequestMockMvc.perform(get("/api/requests/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Update the request
        Request updatedRequest = requestRepository.findOne(request.getId());
        // Disconnect from session so that the updates on updatedRequest are not directly saved in db
        em.detach(updatedRequest);
        updatedRequest
            .requestDate(UPDATED_REQUEST_DATE)
            .viewed(UPDATED_VIEWED)
            .status(UPDATED_STATUS)
            .travelerid(UPDATED_TRAVELERID);
        RequestDTO requestDTO = requestMapper.toDto(updatedRequest);

        restRequestMockMvc.perform(put("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isOk());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate);
        Request testRequest = requestList.get(requestList.size() - 1);
        assertThat(testRequest.getRequestDate()).isEqualTo(UPDATED_REQUEST_DATE);
        assertThat(testRequest.getViewed()).isEqualTo(UPDATED_VIEWED);
        assertThat(testRequest.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testRequest.getTravelerid()).isEqualTo(UPDATED_TRAVELERID);
    }

    @Test
    @Transactional
    public void updateNonExistingRequest() throws Exception {
        int databaseSizeBeforeUpdate = requestRepository.findAll().size();

        // Create the Request
        RequestDTO requestDTO = requestMapper.toDto(request);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restRequestMockMvc.perform(put("/api/requests")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(requestDTO)))
            .andExpect(status().isCreated());

        // Validate the Request in the database
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteRequest() throws Exception {
        // Initialize the database
        requestRepository.saveAndFlush(request);
        int databaseSizeBeforeDelete = requestRepository.findAll().size();

        // Get the request
        restRequestMockMvc.perform(delete("/api/requests/{id}", request.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Request> requestList = requestRepository.findAll();
        assertThat(requestList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Request.class);
        Request request1 = new Request();
        request1.setId(1L);
        Request request2 = new Request();
        request2.setId(request1.getId());
        assertThat(request1).isEqualTo(request2);
        request2.setId(2L);
        assertThat(request1).isNotEqualTo(request2);
        request1.setId(null);
        assertThat(request1).isNotEqualTo(request2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(RequestDTO.class);
        RequestDTO requestDTO1 = new RequestDTO();
        requestDTO1.setId(1L);
        RequestDTO requestDTO2 = new RequestDTO();
        assertThat(requestDTO1).isNotEqualTo(requestDTO2);
        requestDTO2.setId(requestDTO1.getId());
        assertThat(requestDTO1).isEqualTo(requestDTO2);
        requestDTO2.setId(2L);
        assertThat(requestDTO1).isNotEqualTo(requestDTO2);
        requestDTO1.setId(null);
        assertThat(requestDTO1).isNotEqualTo(requestDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(requestMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(requestMapper.fromId(null)).isNull();
    }
}
