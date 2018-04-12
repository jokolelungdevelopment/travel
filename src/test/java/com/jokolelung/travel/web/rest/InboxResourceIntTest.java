package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.Inbox;
import com.jokolelung.travel.repository.InboxRepository;
import com.jokolelung.travel.service.InboxService;
import com.jokolelung.travel.service.dto.InboxDTO;
import com.jokolelung.travel.service.mapper.InboxMapper;
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
import org.springframework.util.Base64Utils;

import javax.persistence.EntityManager;
import java.time.Instant;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static com.jokolelung.travel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the InboxResource REST controller.
 *
 * @see InboxResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class InboxResourceIntTest {

    private static final String DEFAULT_SUBJECT = "AAAAAAAAAA";
    private static final String UPDATED_SUBJECT = "BBBBBBBBBB";

    private static final Instant DEFAULT_POST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private InboxRepository inboxRepository;

    @Autowired
    private InboxMapper inboxMapper;

    @Autowired
    private InboxService inboxService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restInboxMockMvc;

    private Inbox inbox;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final InboxResource inboxResource = new InboxResource(inboxService);
        this.restInboxMockMvc = MockMvcBuilders.standaloneSetup(inboxResource)
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
    public static Inbox createEntity(EntityManager em) {
        Inbox inbox = new Inbox()
            .subject(DEFAULT_SUBJECT)
            .postDate(DEFAULT_POST_DATE);
        return inbox;
    }

    @Before
    public void initTest() {
        inbox = createEntity(em);
    }

    @Test
    @Transactional
    public void createInbox() throws Exception {
        int databaseSizeBeforeCreate = inboxRepository.findAll().size();

        // Create the Inbox
        InboxDTO inboxDTO = inboxMapper.toDto(inbox);
        restInboxMockMvc.perform(post("/api/inboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inboxDTO)))
            .andExpect(status().isCreated());

        // Validate the Inbox in the database
        List<Inbox> inboxList = inboxRepository.findAll();
        assertThat(inboxList).hasSize(databaseSizeBeforeCreate + 1);
        Inbox testInbox = inboxList.get(inboxList.size() - 1);
        assertThat(testInbox.getSubject()).isEqualTo(DEFAULT_SUBJECT);
        assertThat(testInbox.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
    }

    @Test
    @Transactional
    public void createInboxWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = inboxRepository.findAll().size();

        // Create the Inbox with an existing ID
        inbox.setId(1L);
        InboxDTO inboxDTO = inboxMapper.toDto(inbox);

        // An entity with an existing ID cannot be created, so this API call must fail
        restInboxMockMvc.perform(post("/api/inboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inboxDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Inbox in the database
        List<Inbox> inboxList = inboxRepository.findAll();
        assertThat(inboxList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllInboxes() throws Exception {
        // Initialize the database
        inboxRepository.saveAndFlush(inbox);

        // Get all the inboxList
        restInboxMockMvc.perform(get("/api/inboxes?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(inbox.getId().intValue())))
            .andExpect(jsonPath("$.[*].subject").value(hasItem(DEFAULT_SUBJECT.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getInbox() throws Exception {
        // Initialize the database
        inboxRepository.saveAndFlush(inbox);

        // Get the inbox
        restInboxMockMvc.perform(get("/api/inboxes/{id}", inbox.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(inbox.getId().intValue()))
            .andExpect(jsonPath("$.subject").value(DEFAULT_SUBJECT.toString()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingInbox() throws Exception {
        // Get the inbox
        restInboxMockMvc.perform(get("/api/inboxes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateInbox() throws Exception {
        // Initialize the database
        inboxRepository.saveAndFlush(inbox);
        int databaseSizeBeforeUpdate = inboxRepository.findAll().size();

        // Update the inbox
        Inbox updatedInbox = inboxRepository.findOne(inbox.getId());
        // Disconnect from session so that the updates on updatedInbox are not directly saved in db
        em.detach(updatedInbox);
        updatedInbox
            .subject(UPDATED_SUBJECT)
            .postDate(UPDATED_POST_DATE);
        InboxDTO inboxDTO = inboxMapper.toDto(updatedInbox);

        restInboxMockMvc.perform(put("/api/inboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inboxDTO)))
            .andExpect(status().isOk());

        // Validate the Inbox in the database
        List<Inbox> inboxList = inboxRepository.findAll();
        assertThat(inboxList).hasSize(databaseSizeBeforeUpdate);
        Inbox testInbox = inboxList.get(inboxList.size() - 1);
        assertThat(testInbox.getSubject()).isEqualTo(UPDATED_SUBJECT);
        assertThat(testInbox.getPostDate()).isEqualTo(UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingInbox() throws Exception {
        int databaseSizeBeforeUpdate = inboxRepository.findAll().size();

        // Create the Inbox
        InboxDTO inboxDTO = inboxMapper.toDto(inbox);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restInboxMockMvc.perform(put("/api/inboxes")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(inboxDTO)))
            .andExpect(status().isCreated());

        // Validate the Inbox in the database
        List<Inbox> inboxList = inboxRepository.findAll();
        assertThat(inboxList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteInbox() throws Exception {
        // Initialize the database
        inboxRepository.saveAndFlush(inbox);
        int databaseSizeBeforeDelete = inboxRepository.findAll().size();

        // Get the inbox
        restInboxMockMvc.perform(delete("/api/inboxes/{id}", inbox.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Inbox> inboxList = inboxRepository.findAll();
        assertThat(inboxList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Inbox.class);
        Inbox inbox1 = new Inbox();
        inbox1.setId(1L);
        Inbox inbox2 = new Inbox();
        inbox2.setId(inbox1.getId());
        assertThat(inbox1).isEqualTo(inbox2);
        inbox2.setId(2L);
        assertThat(inbox1).isNotEqualTo(inbox2);
        inbox1.setId(null);
        assertThat(inbox1).isNotEqualTo(inbox2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(InboxDTO.class);
        InboxDTO inboxDTO1 = new InboxDTO();
        inboxDTO1.setId(1L);
        InboxDTO inboxDTO2 = new InboxDTO();
        assertThat(inboxDTO1).isNotEqualTo(inboxDTO2);
        inboxDTO2.setId(inboxDTO1.getId());
        assertThat(inboxDTO1).isEqualTo(inboxDTO2);
        inboxDTO2.setId(2L);
        assertThat(inboxDTO1).isNotEqualTo(inboxDTO2);
        inboxDTO1.setId(null);
        assertThat(inboxDTO1).isNotEqualTo(inboxDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(inboxMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(inboxMapper.fromId(null)).isNull();
    }
}
