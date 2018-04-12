package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.JourneyService;
import com.jokolelung.travel.domain.Journey;
import com.jokolelung.travel.repository.JourneyRepository;
import com.jokolelung.travel.service.dto.JourneyDTO;
import com.jokolelung.travel.service.mapper.JourneyMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Journey.
 */
@Service
@Transactional
public class JourneyServiceImpl implements JourneyService {

    private final Logger log = LoggerFactory.getLogger(JourneyServiceImpl.class);

    private final JourneyRepository journeyRepository;

    private final JourneyMapper journeyMapper;

    public JourneyServiceImpl(JourneyRepository journeyRepository, JourneyMapper journeyMapper) {
        this.journeyRepository = journeyRepository;
        this.journeyMapper = journeyMapper;
    }

    /**
     * Save a journey.
     *
     * @param journeyDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public JourneyDTO save(JourneyDTO journeyDTO) {
        log.debug("Request to save Journey : {}", journeyDTO);
        Journey journey = journeyMapper.toEntity(journeyDTO);
        journey = journeyRepository.save(journey);
        return journeyMapper.toDto(journey);
    }

    /**
     * Get all the journeys.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<JourneyDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Journeys");
        return journeyRepository.findAll(pageable)
            .map(journeyMapper::toDto);
    }

    /**
     * Get one journey by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public JourneyDTO findOne(Long id) {
        log.debug("Request to get Journey : {}", id);
        Journey journey = journeyRepository.findOne(id);
        return journeyMapper.toDto(journey);
    }

    /**
     * Delete the journey by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Journey : {}", id);
        journeyRepository.delete(id);
    }
}
