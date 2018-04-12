package com.jokolelung.travel.web.rest;

import com.jokolelung.travel.TravelApp;

import com.jokolelung.travel.domain.UserInfo;
import com.jokolelung.travel.repository.UserInfoRepository;
import com.jokolelung.travel.service.UserInfoService;
import com.jokolelung.travel.service.dto.UserInfoDTO;
import com.jokolelung.travel.service.mapper.UserInfoMapper;
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
import java.util.List;

import static com.jokolelung.travel.web.rest.TestUtil.createFormattingConversionService;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import com.jokolelung.travel.domain.enumeration.StatusUser;
/**
 * Test class for the UserInfoResource REST controller.
 *
 * @see UserInfoResource
 */
@RunWith(SpringRunner.class)
@SpringBootTest(classes = TravelApp.class)
public class UserInfoResourceIntTest {

    private static final String DEFAULT_FULLNAME = "AAAAAAAAAA";
    private static final String UPDATED_FULLNAME = "BBBBBBBBBB";

    private static final String DEFAULT_BIRTHDATE = "AAAAAAAAAA";
    private static final String UPDATED_BIRTHDATE = "BBBBBBBBBB";

    private static final String DEFAULT_PHONE_NUMBER = "AAAAAAAAAA";
    private static final String UPDATED_PHONE_NUMBER = "BBBBBBBBBB";

    private static final StatusUser DEFAULT_STATUS = StatusUser.NOT;
    private static final StatusUser UPDATED_STATUS = StatusUser.VERIFIED;

    private static final Double DEFAULT_BALANCE = 1D;
    private static final Double UPDATED_BALANCE = 2D;

    private static final String DEFAULT_IMGURL = "AAAAAAAAAA";
    private static final String UPDATED_IMGURL = "BBBBBBBBBB";

    private static final String DEFAULT_GMAIL_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_GMAIL_TOKEN = "BBBBBBBBBB";

    private static final String DEFAULT_FACEBOOK_TOKEN = "AAAAAAAAAA";
    private static final String UPDATED_FACEBOOK_TOKEN = "BBBBBBBBBB";

    @Autowired
    private UserInfoRepository userInfoRepository;

    @Autowired
    private UserInfoMapper userInfoMapper;

    @Autowired
    private UserInfoService userInfoService;

    @Autowired
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Autowired
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private ExceptionTranslator exceptionTranslator;

    @Autowired
    private EntityManager em;

    private MockMvc restUserInfoMockMvc;

    private UserInfo userInfo;

    @Before
    public void setup() {
        MockitoAnnotations.initMocks(this);
        final UserInfoResource userInfoResource = new UserInfoResource(userInfoService);
        this.restUserInfoMockMvc = MockMvcBuilders.standaloneSetup(userInfoResource)
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
    public static UserInfo createEntity(EntityManager em) {
        UserInfo userInfo = new UserInfo()
            .fullname(DEFAULT_FULLNAME)
            .birthdate(DEFAULT_BIRTHDATE)
            .phoneNumber(DEFAULT_PHONE_NUMBER)
            .status(DEFAULT_STATUS)
            .balance(DEFAULT_BALANCE)
            .imgurl(DEFAULT_IMGURL)
            .gmailToken(DEFAULT_GMAIL_TOKEN)
            .facebookToken(DEFAULT_FACEBOOK_TOKEN);
        return userInfo;
    }

    @Before
    public void initTest() {
        userInfo = createEntity(em);
    }

    @Test
    @Transactional
    public void createUserInfo() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate + 1);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getFullname()).isEqualTo(DEFAULT_FULLNAME);
        assertThat(testUserInfo.getBirthdate()).isEqualTo(DEFAULT_BIRTHDATE);
        assertThat(testUserInfo.getPhoneNumber()).isEqualTo(DEFAULT_PHONE_NUMBER);
        assertThat(testUserInfo.getStatus()).isEqualTo(DEFAULT_STATUS);
        assertThat(testUserInfo.getBalance()).isEqualTo(DEFAULT_BALANCE);
        assertThat(testUserInfo.getImgurl()).isEqualTo(DEFAULT_IMGURL);
        assertThat(testUserInfo.getGmailToken()).isEqualTo(DEFAULT_GMAIL_TOKEN);
        assertThat(testUserInfo.getFacebookToken()).isEqualTo(DEFAULT_FACEBOOK_TOKEN);
    }

    @Test
    @Transactional
    public void createUserInfoWithExistingId() throws Exception {
        int databaseSizeBeforeCreate = userInfoRepository.findAll().size();

        // Create the UserInfo with an existing ID
        userInfo.setId(1L);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // An entity with an existing ID cannot be created, so this API call must fail
        restUserInfoMockMvc.perform(post("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isBadRequest());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeCreate);
    }

    @Test
    @Transactional
    public void getAllUserInfos() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get all the userInfoList
        restUserInfoMockMvc.perform(get("/api/user-infos?sort=id,desc"))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.[*].id").value(hasItem(userInfo.getId().intValue())))
            .andExpect(jsonPath("$.[*].fullname").value(hasItem(DEFAULT_FULLNAME.toString())))
            .andExpect(jsonPath("$.[*].birthdate").value(hasItem(DEFAULT_BIRTHDATE.toString())))
            .andExpect(jsonPath("$.[*].phoneNumber").value(hasItem(DEFAULT_PHONE_NUMBER.toString())))
            .andExpect(jsonPath("$.[*].status").value(hasItem(DEFAULT_STATUS.toString())))
            .andExpect(jsonPath("$.[*].balance").value(hasItem(DEFAULT_BALANCE.doubleValue())))
            .andExpect(jsonPath("$.[*].imgurl").value(hasItem(DEFAULT_IMGURL.toString())))
            .andExpect(jsonPath("$.[*].gmailToken").value(hasItem(DEFAULT_GMAIL_TOKEN.toString())))
            .andExpect(jsonPath("$.[*].facebookToken").value(hasItem(DEFAULT_FACEBOOK_TOKEN.toString())));
    }

    @Test
    @Transactional
    public void getUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);

        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", userInfo.getId()))
            .andExpect(status().isOk())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8_VALUE))
            .andExpect(jsonPath("$.id").value(userInfo.getId().intValue()))
            .andExpect(jsonPath("$.fullname").value(DEFAULT_FULLNAME.toString()))
            .andExpect(jsonPath("$.birthdate").value(DEFAULT_BIRTHDATE.toString()))
            .andExpect(jsonPath("$.phoneNumber").value(DEFAULT_PHONE_NUMBER.toString()))
            .andExpect(jsonPath("$.status").value(DEFAULT_STATUS.toString()))
            .andExpect(jsonPath("$.balance").value(DEFAULT_BALANCE.doubleValue()))
            .andExpect(jsonPath("$.imgurl").value(DEFAULT_IMGURL.toString()))
            .andExpect(jsonPath("$.gmailToken").value(DEFAULT_GMAIL_TOKEN.toString()))
            .andExpect(jsonPath("$.facebookToken").value(DEFAULT_FACEBOOK_TOKEN.toString()));
    }

    @Test
    @Transactional
    public void getNonExistingUserInfo() throws Exception {
        // Get the userInfo
        restUserInfoMockMvc.perform(get("/api/user-infos/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound());
    }

    @Test
    @Transactional
    public void updateUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Update the userInfo
        UserInfo updatedUserInfo = userInfoRepository.findOne(userInfo.getId());
        // Disconnect from session so that the updates on updatedUserInfo are not directly saved in db
        em.detach(updatedUserInfo);
        updatedUserInfo
            .fullname(UPDATED_FULLNAME)
            .birthdate(UPDATED_BIRTHDATE)
            .phoneNumber(UPDATED_PHONE_NUMBER)
            .status(UPDATED_STATUS)
            .balance(UPDATED_BALANCE)
            .imgurl(UPDATED_IMGURL)
            .gmailToken(UPDATED_GMAIL_TOKEN)
            .facebookToken(UPDATED_FACEBOOK_TOKEN);
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(updatedUserInfo);

        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isOk());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate);
        UserInfo testUserInfo = userInfoList.get(userInfoList.size() - 1);
        assertThat(testUserInfo.getFullname()).isEqualTo(UPDATED_FULLNAME);
        assertThat(testUserInfo.getBirthdate()).isEqualTo(UPDATED_BIRTHDATE);
        assertThat(testUserInfo.getPhoneNumber()).isEqualTo(UPDATED_PHONE_NUMBER);
        assertThat(testUserInfo.getStatus()).isEqualTo(UPDATED_STATUS);
        assertThat(testUserInfo.getBalance()).isEqualTo(UPDATED_BALANCE);
        assertThat(testUserInfo.getImgurl()).isEqualTo(UPDATED_IMGURL);
        assertThat(testUserInfo.getGmailToken()).isEqualTo(UPDATED_GMAIL_TOKEN);
        assertThat(testUserInfo.getFacebookToken()).isEqualTo(UPDATED_FACEBOOK_TOKEN);
    }

    @Test
    @Transactional
    public void updateNonExistingUserInfo() throws Exception {
        int databaseSizeBeforeUpdate = userInfoRepository.findAll().size();

        // Create the UserInfo
        UserInfoDTO userInfoDTO = userInfoMapper.toDto(userInfo);

        // If the entity doesn't have an ID, it will be created instead of just being updated
        restUserInfoMockMvc.perform(put("/api/user-infos")
            .contentType(TestUtil.APPLICATION_JSON_UTF8)
            .content(TestUtil.convertObjectToJsonBytes(userInfoDTO)))
            .andExpect(status().isCreated());

        // Validate the UserInfo in the database
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeUpdate + 1);
    }

    @Test
    @Transactional
    public void deleteUserInfo() throws Exception {
        // Initialize the database
        userInfoRepository.saveAndFlush(userInfo);
        int databaseSizeBeforeDelete = userInfoRepository.findAll().size();

        // Get the userInfo
        restUserInfoMockMvc.perform(delete("/api/user-infos/{id}", userInfo.getId())
            .accept(TestUtil.APPLICATION_JSON_UTF8))
            .andExpect(status().isOk());

        // Validate the database is empty
        List<UserInfo> userInfoList = userInfoRepository.findAll();
        assertThat(userInfoList).hasSize(databaseSizeBeforeDelete - 1);
    }

    @Test
    @Transactional
    public void equalsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfo.class);
        UserInfo userInfo1 = new UserInfo();
        userInfo1.setId(1L);
        UserInfo userInfo2 = new UserInfo();
        userInfo2.setId(userInfo1.getId());
        assertThat(userInfo1).isEqualTo(userInfo2);
        userInfo2.setId(2L);
        assertThat(userInfo1).isNotEqualTo(userInfo2);
        userInfo1.setId(null);
        assertThat(userInfo1).isNotEqualTo(userInfo2);
    }

    @Test
    @Transactional
    public void dtoEqualsVerifier() throws Exception {
        TestUtil.equalsVerifier(UserInfoDTO.class);
        UserInfoDTO userInfoDTO1 = new UserInfoDTO();
        userInfoDTO1.setId(1L);
        UserInfoDTO userInfoDTO2 = new UserInfoDTO();
        assertThat(userInfoDTO1).isNotEqualTo(userInfoDTO2);
        userInfoDTO2.setId(userInfoDTO1.getId());
        assertThat(userInfoDTO1).isEqualTo(userInfoDTO2);
        userInfoDTO2.setId(2L);
        assertThat(userInfoDTO1).isNotEqualTo(userInfoDTO2);
        userInfoDTO1.setId(null);
        assertThat(userInfoDTO1).isNotEqualTo(userInfoDTO2);
    }

    @Test
    @Transactional
    public void testEntityFromId() {
        assertThat(userInfoMapper.fromId(42L).getId()).isEqualTo(42);
        assertThat(userInfoMapper.fromId(null)).isNull();
    }
}
