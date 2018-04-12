package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.Messages;
import com.jokolelung.travel.repository.MessagesRepository;
import com.jokolelung.travel.service.MessagesService;
import com.jokolelung.travel.service.dto.MessagesDTO;
import com.jokolelung.travel.service.mapper.MessagesMapper;
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
 * Test class for the MessagesResource REST controller.
 *
 * @see MessagesResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class MessagesResourceIntTest {

    private static final String DEFAULT_TEXT = "AAAAAAAAAA";
    private static final String UPDATED_TEXT = "BBBBBBBBBB";

    private static final Instant DEFAULT_POST_DATE = Instant.ofEpochMilli(0L);
    private static final Instant UPDATED_POST_DATE = Instant.now().truncatedTo(ChronoUnit.MILLIS);

    @Autowired
    private MessagesRepository messagesRepository;

    @Autowired
    private MessagesMapper messagesMapper;

    @Autowired
    private MessagesService messagesService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restMessagesMockMvc;

    private Messages messages;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final MessagesResource messagesResource = new MessagesResource(messagesService);
        this.restMessagesMockMvc = MockMvcBuilders.standaloneSetup(messagesResource)
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
    public static Messages createEntity(EntityManager em) {
        Messages messages = new Messages()
            .text(DEFAULT_TEXT)
            .postDate(DEFAULT_POST_DATE);
        return messages;
    }

    @Before
    public void initTest() {
        messages = createEntity(em);
    }

    @Test
    @Transactional
    public void createMessages() throws Exception {
        int databaseSizeBeforeCreate = messagesRepository.findAll().size();

        // Create the Messages
        MessagesDTO messagesDTO = messagesMapper.toDto(messages);
        restMessagesMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeCreate + 1);
        Messages testMessages = messagesList.get(messagesList.size() - 1);
        assertThat(testMessages.getText()).isEqualTo(DEFAULT_TEXT);
        assertThat(testMessages.getPostDate()).isEqualTo(DEFAULT_POST_DATE);
    }

    @Test
    @Transactional
    public void createMessagesWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = messagesRepository.findAll().size();

        // Create the Messages with an existing ID
        messages.setId(1L);
        MessagesDTO messagesDTO = messagesMapper.toDto(messages);

        // An entity with an existing ID cannot be created, so this API call must fail
        restMessagesMockMvc.perform(post("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get all the messagesList
        restMessagesMockMvc.perform(get("/api/messages?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(messages.getId().intValue())))
            .andExpect(jsonPath("$.[*].text").value(hasItem(DEFAULT_TEXT.toString())))
            .andExpect(jsonPath("$.[*].postDate").value(hasItem(DEFAULT_POST_DATE.toString())));
    }

    @Test
    @Transactional
    public void getMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);

        // Get the messages
        restMessagesMockMvc.perform(get("/api/messages/{id}", messages.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(messages.getId().intValue()))
            .andExpect(jsonPath("$.text").value(DEFAULT_TEXT.toString()))
            .andExpect(jsonPath("$.postDate").value(DEFAULT_POST_DATE.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingMessages() throws Exception {
        // Get the messages
        restMessagesMockMvc.perform(get("/api/messages/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);
        int databaseSizeBeforeUpdate = messagesRepository.findAll().size();

        // Update the messages
        Messages updatedMessages = messagesRepository.findOne(messages.getId());
        // Disconnect from session so that the updates on updatedMessages are not directly saved in db
        em.detach(updatedMessages);
        updatedMessages
            .text(UPDATED_TEXT)
            .postDate(UPDATED_POST_DATE);
        MessagesDTO messagesDTO = messagesMapper.toDto(updatedMessages);

        restMessagesMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isOk());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeUpdate);
        Messages testMessages = messagesList.get(messagesList.size() - 1);
        assertThat(testMessages.getText()).isEqualTo(UPDATED_TEXT);
        assertThat(testMessages.getPostDate()).isEqualTo(UPDATED_POST_DATE);
    }

    @Test
    @Transactional
    public void updateNonExistingMessages() throws Exception {
        int databaseSizeBeforeUpdate = messagesRepository.findAll().size();

        // Create the Messages
        MessagesDTO messagesDTO = messagesMapper.toDto(messages);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restMessagesMockMvc.perform(put("/api/messages")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(messagesDTO)))
            .andExpect(status().isCreated());

        // Validate the Messages in the database
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteMessages() throws Exception {
        // Initialize the database
        messagesRepository.saveAndFlush(messages);
        int databaseSizeBeforeDelete = messagesRepository.findAll().size();

        // Get the messages
        restMessagesMockMvc.perform(delete("/api/messages/{id}", messages.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Messages> messagesList = messagesRepository.findAll();
        assertThat(messagesList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Messages.class);
        Messages messages1 = new Messages();
        messages1.setId(1L);
        Messages messages2 = new Messages();
        messages2.setId(messages1.getId());
        assertThat(messages1).isEqualTo(messages2);
        messages2.setId(2L);
        assertThat(messages1).isNotEqualTo(messages2);
        messages1.setId(null);
        assertThat(messages1).isNotEqualTo(messages2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(MessagesDTO.class);
        MessagesDTO messagesDTO1 = new MessagesDTO();
        messagesDTO1.setId(1L);
        MessagesDTO messagesDTO2 = new MessagesDTO();
        assertThat(messagesDTO1).isNotEqualTo(messagesDTO2);
        messagesDTO2.setId(messagesDTO1.getId());
        assertThat(messagesDTO1).isEqualTo(messagesDTO2);
        messagesDTO2.setId(2L);
        assertThat(messagesDTO1).isNotEqualTo(messagesDTO2);
        messagesDTO1.setId(null);
        assertThat(messagesDTO1).isNotEqualTo(messagesDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(messagesMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(messagesMapper.fromId(null)).isNull();
    }
}
