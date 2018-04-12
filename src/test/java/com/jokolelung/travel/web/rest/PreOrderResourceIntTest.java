package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.PreOrder;
import com.jokolelung.travel.repository.PreOrderRepository;
import com.jokolelung.travel.service.PreOrderService;
import com.jokolelung.travel.service.dto.PreOrderDTO;
import com.jokolelung.travel.service.mapper.PreOrderMapper;
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
 * Test class for the PreOrderResource REST controller.
 *
 * @see PreOrderResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class PreOrderResourceIntTest {

    private static final Instant DEFAULT_ORDER_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_ORDER_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final Status UPDATED_STATUS = Status.REQUSTED;

    @Autowired
    private PreOrderRepository preOrderRepository;

    @Autowired
    private PreOrderMapper preOrderMapper;

    @Autowired
    private PreOrderService preOrderService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restPreOrderMockMvc;

    private PreOrder preOrder;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final PreOrderResource preOrderResource = new PreOrderResource(preOrderService);
        this.restPreOrderMockMvc = MockMvcBuilders.standaloneSetup(preOrderResource)
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
    public static PreOrder createEntity(EntityManager em) {
        PreOrder preOrder = new PreOrder()
            .orderDate(DEFAULT_ORDER_DATE)
            .status(DEFAULT_STATUS);
        return preOrder;
    }

    @Before
    public void initTest() {
        preOrder = createEntity(em);
    }

    @Test
    @Transactional
    public void createPreOrder() throws Exception {
        int databaseSizeBeforeCreate = preOrderRepository.findAll().size();

        // Create the PreOrder
        PreOrderDTO preOrderDTO = preOrderMapper.toDto(preOrder);
        restPreOrderMockMvc.perform(post("/api/pre-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PreOrder in the database
        List<PreOrder> preOrderList = preOrderRepository.findAll();
        assertThat(preOrderList).hasSize(databaseSizeBeforeCreate + 1);
        PreOrder testPreOrder = preOrderList.get(preOrderList.size() - 1);
        assertThat(testPreOrder.getOrderDate()).isEqualTo(DEFAULT_ORDER_DATE);
        assertThat(testPreOrder.getStatus()).isEqualTo(DEFAULT_STATUS);
    }

    @Test
    @Transactional
    public void createPreOrderWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = preOrderRepository.findAll().size();

        // Create the PreOrder with an existing ID
        preOrder.setId(1L);
        PreOrderDTO preOrderDTO = preOrderMapper.toDto(preOrder);

        // An entity with an existing ID cannot be created, so this API call must fail
        restPreOrderMockMvc.perform(post("/api/pre-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preOrderDTO)))
            .andExpect(status().isBadRequest());

        // Validate the PreOrder in the database
        List<PreOrder> preOrderList = preOrderRepository.findAll();
        assertThat(preOrderList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllPreOrders() throws Exception {
        // Initialize the database
        preOrderRepository.saveAndFlush(preOrder);

        // Get all the preOrderList
        restPreOrderMockMvc.perform(get("/api/pre-orders?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(preOrder.getId().intValue())))
            .andExpect(jsonPath("$.[*].orderDate").value(hasItem(DEFAULT_ORDER_DATE.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())));
    }

    @Test
    @Transactional
    public void getPreOrder() throws Exception {
        // Initialize the database
        preOrderRepository.saveAndFlush(preOrder);

        // Get the preOrder
        restPreOrderMockMvc.perform(get("/api/pre-orders/{id}", preOrder.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(preOrder.getId().intValue()))
            .andExpect(jsonPath("$.orderDate").value(DEFAULT_ORDER_DATE.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingPreOrder() throws Exception {
        // Get the preOrder
        restPreOrderMockMvc.perform(get("/api/pre-orders/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updatePreOrder() throws Exception {
        // Initialize the database
        preOrderRepository.saveAndFlush(preOrder);
        int databaseSizeBeforeUpdate = preOrderRepository.findAll().size();

        // Update the preOrder
        PreOrder updatedPreOrder = preOrderRepository.findOne(preOrder.getId());
        // Disconnect from session so that the updates on updatedPreOrder are not directly saved in db
        em.detach(updatedPreOrder);
        updatedPreOrder
            .orderDate(UPDATED_ORDER_DATE)
            .status(UPDATED_STATUS);
        PreOrderDTO preOrderDTO = preOrderMapper.toDto(updatedPreOrder);

        restPreOrderMockMvc.perform(put("/api/pre-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preOrderDTO)))
            .andExpect(status().isOk());

        // Validate the PreOrder in the database
        List<PreOrder> preOrderList = preOrderRepository.findAll();
        assertThat(preOrderList).hasSize(databaseSizeBeforeUpdate);
        PreOrder testPreOrder = preOrderList.get(preOrderList.size() - 1);
        assertThat(testPreOrder.getOrderDate()).isEqualTo(UPDATED_ORDER_DATE);
        assertThat(testPreOrder.getStatus()).isEqualTo(UPDATED_STATUS);
    }

    @Test
    @Transactional
    public void updateNonExistingPreOrder() throws Exception {
        int databaseSizeBeforeUpdate = preOrderRepository.findAll().size();

        // Create the PreOrder
        PreOrderDTO preOrderDTO = preOrderMapper.toDto(preOrder);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restPreOrderMockMvc.perform(put("/api/pre-orders")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(preOrderDTO)))
            .andExpect(status().isCreated());

        // Validate the PreOrder in the database
        List<PreOrder> preOrderList = preOrderRepository.findAll();
        assertThat(preOrderList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deletePreOrder() throws Exception {
        // Initialize the database
        preOrderRepository.saveAndFlush(preOrder);
        int databaseSizeBeforeDelete = preOrderRepository.findAll().size();

        // Get the preOrder
        restPreOrderMockMvc.perform(delete("/api/pre-orders/{id}", preOrder.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<PreOrder> preOrderList = preOrderRepository.findAll();
        assertThat(preOrderList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreOrder.class);
        PreOrder preOrder1 = new PreOrder();
        preOrder1.setId(1L);
        PreOrder preOrder2 = new PreOrder();
        preOrder2.setId(preOrder1.getId());
        assertThat(preOrder1).isEqualTo(preOrder2);
        preOrder2.setId(2L);
        assertThat(preOrder1).isNotEqualTo(preOrder2);
        preOrder1.setId(null);
        assertThat(preOrder1).isNotEqualTo(preOrder2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(PreOrderDTO.class);
        PreOrderDTO preOrderDTO1 = new PreOrderDTO();
        preOrderDTO1.setId(1L);
        PreOrderDTO preOrderDTO2 = new PreOrderDTO();
        assertThat(preOrderDTO1).isNotEqualTo(preOrderDTO2);
        preOrderDTO2.setId(preOrderDTO1.getId());
        assertThat(preOrderDTO1).isEqualTo(preOrderDTO2);
        preOrderDTO2.setId(2L);
        assertThat(preOrderDTO1).isNotEqualTo(preOrderDTO2);
        preOrderDTO1.setId(null);
        assertThat(preOrderDTO1).isNotEqualTo(preOrderDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(preOrderMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(preOrderMapper.fromId(null)).isNull();
    }
}
