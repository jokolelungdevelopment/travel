package com.jokolelung.travel.service;

import com.jokolelung.travel.service.dto.SlideDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Slide.
 */
public interface SlideService {

    /**
     * Save a slide.
     *
     * @param slideDTO the entity to save
     * @return the persisted entity
     */
    SlideDTO save(SlideDTO slideDTO);

    /**
     * Get all the slides.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<SlideDTO> findAll(Pageable pageable);

    /**
     * Get the "id" slide.
     *
     * @param id the id of the entity
     * @return the entity
     */
    SlideDTO findOne(Long id);

    /**
     * Delete the "id" slide.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
