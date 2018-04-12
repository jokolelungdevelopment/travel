package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.SlideService;
import com.jokolelung.travel.domain.Slide;
import com.jokolelung.travel.repository.SlideRepository;
import com.jokolelung.travel.service.dto.SlideDTO;
import com.jokolelung.travel.service.mapper.SlideMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Slide.
 */
@Service
@Transactional
public class SlideServiceImpl implements SlideService {

    private final Logger log = LoggerFactory.getLogger(SlideServiceImpl.class);

    private final SlideRepository slideRepository;

    private final SlideMapper slideMapper;

    public SlideServiceImpl(SlideRepository slideRepository, SlideMapper slideMapper) {
        this.slideRepository = slideRepository;
        this.slideMapper = slideMapper;
    }

    /**
     * Save a slide.
     *
     * @param slideDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public SlideDTO save(SlideDTO slideDTO) {
        log.debug("Request to save Slide : {}", slideDTO);
        Slide slide = slideMapper.toEntity(slideDTO);
        slide = slideRepository.save(slide);
        return slideMapper.toDto(slide);
    }

    /**
     * Get all the slides.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<SlideDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Slides");
        return slideRepository.findAll(pageable)
            .map(slideMapper::toDto);
    }

    /**
     * Get one slide by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public SlideDTO findOne(Long id) {
        log.debug("Request to get Slide : {}", id);
        Slide slide = slideRepository.findOne(id);
        return slideMapper.toDto(slide);
    }

    /**
     * Delete the slide by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Slide : {}", id);
        slideRepository.delete(id);
    }
}
