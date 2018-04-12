package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.UserInfoService;
import com.jokolelung.travel.domain.UserInfo;
import com.jokolelung.travel.repository.UserInfoRepository;
import com.jokolelung.travel.service.dto.UserInfoDTO;
import com.jokolelung.travel.service.mapper.UserInfoMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing UserInfo.
 */
@Service
@Transactional
public class UserInfoServiceImpl implements UserInfoService {

    private final Logger log = LoggerFactory.getLogger(UserInfoServiceImpl.class);

    private final UserInfoRepository userInfoRepository;

    private final UserInfoMapper userInfoMapper;

    public UserInfoServiceImpl(UserInfoRepository userInfoRepository, UserInfoMapper userInfoMapper) {
        this.userInfoRepository = userInfoRepository;
        this.userInfoMapper = userInfoMapper;
    }

    /**
     * Save a userInfo.
     *
     * @param userInfoDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public UserInfoDTO save(UserInfoDTO userInfoDTO) {
        log.debug("Request to save UserInfo : {}", userInfoDTO);
        UserInfo userInfo = userInfoMapper.toEntity(userInfoDTO);
        userInfo = userInfoRepository.save(userInfo);
        return userInfoMapper.toDto(userInfo);
    }

    /**
     * Get all the userInfos.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<UserInfoDTO> findAll(Pageable pageable) {
        log.debug("Request to get all UserInfos");
        return userInfoRepository.findAll(pageable)
            .map(userInfoMapper::toDto);
    }

    /**
     * Get one userInfo by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public UserInfoDTO findOne(Long id) {
        log.debug("Request to get UserInfo : {}", id);
        UserInfo userInfo = userInfoRepository.findOne(id);
        return userInfoMapper.toDto(userInfo);
    }

    /**
     * Delete the userInfo by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete UserInfo : {}", id);
        userInfoRepository.delete(id);
    }
}
