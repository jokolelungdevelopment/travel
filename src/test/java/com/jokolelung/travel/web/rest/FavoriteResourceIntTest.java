package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.Favorite;
import com.jokolelung.travel.repository.FavoriteRepository;
import com.jokolelung.travel.service.FavoriteService;
import com.jokolelung.travel.service.dto.FavoriteDTO;
import com.jokolelung.travel.service.mapper.FavoriteMapper;
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
import java.util.List;

import static com.jokolelung.travel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

/**
 * Test class for the FavoriteResource REST controller.
 *
 * @see FavoriteResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class FavoriteResourceIntTest {

    private static final Long DEFAULT_REQUESTID = 1L;
    private static final Long UPDATED_REQUESTID = 2L;

    private static final Long DEFAULT_PREORDERID = 1L;
    private static final Long UPDATED_PREORDERID = 2L;

    @Autowired
    private FavoriteRepository favoriteRepository;

    @Autowired
    private FavoriteMapper favoriteMapper;

    @Autowired
    private FavoriteService favoriteService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restFavoriteMockMvc;

    private Favorite favorite;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final FavoriteResource favoriteResource = new FavoriteResource(favoriteService);
        this.restFavoriteMockMvc = MockMvcBuilders.standaloneSetup(favoriteResource)
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
    public static Favorite createEntity(EntityManager em) {
        Favorite favorite = new Favorite()
            .requestid(DEFAULT_REQUESTID)
            .preorderid(DEFAULT_PREORDERID);
        return favorite;
    }

    @Before
    public void initTest() {
        favorite = createEntity(em);
    }

    @Test
    @Transactional
    public void createFavorite() throws Exception {
        int databaseSizeBeforeCreate = favoriteRepository.findAll().size();

        // Create the Favorite
        FavoriteDTO favoriteDTO = favoriteMapper.toDto(favorite);
        restFavoriteMockMvc.perform(post("/api/favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteDTO)))
            .andExpect(status().isCreated());

        // Validate the Favorite in the database
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList).hasSize(databaseSizeBeforeCreate + 1);
        Favorite testFavorite = favoriteList.get(favoriteList.size() - 1);
        assertThat(testFavorite.getRequestid()).isEqualTo(DEFAULT_REQUESTID);
        assertThat(testFavorite.getPreorderid()).isEqualTo(DEFAULT_PREORDERID);
    }

    @Test
    @Transactional
    public void createFavoriteWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = favoriteRepository.findAll().size();

        // Create the Favorite with an existing ID
        favorite.setId(1L);
        FavoriteDTO favoriteDTO = favoriteMapper.toDto(favorite);

        // An entity with an existing ID cannot be created, so this API call must fail
        restFavoriteMockMvc.perform(post("/api/favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteDTO)))
            .andExpect(status().isBadRequest());

        // Validate the Favorite in the database
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllFavorites() throws Exception {
        // Initialize the database
        favoriteRepository.saveAndFlush(favorite);

        // Get all the favoriteList
        restFavoriteMockMvc.perform(get("/api/favorites?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(favorite.getId().intValue())))
            .andExpect(jsonPath("$.[*].requestid").value(hasItem(DEFAULT_REQUESTID.intValue())))
            .andExpect(jsonPath("$.[*].preorderid").value(hasItem(DEFAULT_PREORDERID.intValue())));
    }

    @Test
    @Transactional
    public void getFavorite() throws Exception {
        // Initialize the database
        favoriteRepository.saveAndFlush(favorite);

        // Get the favorite
        restFavoriteMockMvc.perform(get("/api/favorites/{id}", favorite.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(favorite.getId().intValue()))
            .andExpect(jsonPath("$.requestid").value(DEFAULT_REQUESTID.intValue()))
            .andExpect(jsonPath("$.preorderid").value(DEFAULT_PREORDERID.intValue()));
    }

    @Test
    @Transactional
    public void getNonExistingFavorite() throws Exception {
        // Get the favorite
        restFavoriteMockMvc.perform(get("/api/favorites/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateFavorite() throws Exception {
        // Initialize the database
        favoriteRepository.saveAndFlush(favorite);
        int databaseSizeBeforeUpdate = favoriteRepository.findAll().size();

        // Update the favorite
        Favorite updatedFavorite = favoriteRepository.findOne(favorite.getId());
        // Disconnect from session so that the updates on updatedFavorite are not directly saved in db
        em.detach(updatedFavorite);
        updatedFavorite
            .requestid(UPDATED_REQUESTID)
            .preorderid(UPDATED_PREORDERID);
        FavoriteDTO favoriteDTO = favoriteMapper.toDto(updatedFavorite);

        restFavoriteMockMvc.perform(put("/api/favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteDTO)))
            .andExpect(status().isOk());

        // Validate the Favorite in the database
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList).hasSize(databaseSizeBeforeUpdate);
        Favorite testFavorite = favoriteList.get(favoriteList.size() - 1);
        assertThat(testFavorite.getRequestid()).isEqualTo(UPDATED_REQUESTID);
        assertThat(testFavorite.getPreorderid()).isEqualTo(UPDATED_PREORDERID);
    }

    @Test
    @Transactional
    public void updateNonExistingFavorite() throws Exception {
        int databaseSizeBeforeUpdate = favoriteRepository.findAll().size();

        // Create the Favorite
        FavoriteDTO favoriteDTO = favoriteMapper.toDto(favorite);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restFavoriteMockMvc.perform(put("/api/favorites")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(favoriteDTO)))
            .andExpect(status().isCreated());

        // Validate the Favorite in the database
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteFavorite() throws Exception {
        // Initialize the database
        favoriteRepository.saveAndFlush(favorite);
        int databaseSizeBeforeDelete = favoriteRepository.findAll().size();

        // Get the favorite
        restFavoriteMockMvc.perform(delete("/api/favorites/{id}", favorite.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<Favorite> favoriteList = favoriteRepository.findAll();
        assertThat(favoriteList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(Favorite.class);
        Favorite favorite1 = new Favorite();
        favorite1.setId(1L);
        Favorite favorite2 = new Favorite();
        favorite2.setId(favorite1.getId());
        assertThat(favorite1).isEqualTo(favorite2);
        favorite2.setId(2L);
        assertThat(favorite1).isNotEqualTo(favorite2);
        favorite1.setId(null);
        assertThat(favorite1).isNotEqualTo(favorite2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(FavoriteDTO.class);
        FavoriteDTO favoriteDTO1 = new FavoriteDTO();
        favoriteDTO1.setId(1L);
        FavoriteDTO favoriteDTO2 = new FavoriteDTO();
        assertThat(favoriteDTO1).isNotEqualTo(favoriteDTO2);
        favoriteDTO2.setId(favoriteDTO1.getId());
        assertThat(favoriteDTO1).isEqualTo(favoriteDTO2);
        favoriteDTO2.setId(2L);
        assertThat(favoriteDTO1).isNotEqualTo(favoriteDTO2);
        favoriteDTO1.setId(null);
        assertThat(favoriteDTO1).isNotEqualTo(favoriteDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(favoriteMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(favoriteMapper.fromId(null)).isNull();
    }
}
