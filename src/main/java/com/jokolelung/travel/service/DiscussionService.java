package com.jokolelung.travel.service;

import com.jokolelung.travel.service.dto.DiscussionDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Discussion.
 */
public interface DiscussionService {

    /**
     * Save a discussion.
     *
     * @param discussionDTO the entity to save
     * @return the persisted entity
     */
    DiscussionDTO save(DiscussionDTO discussionDTO);

    /**
     * Get all the discussions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<DiscussionDTO> findAll(Pageable pageable);

    /**
     * Get the "id" discussion.
     *
     * @param id the id of the entity
     * @return the entity
     */
    DiscussionDTO findOne(Long id);

    /**
     * Delete the "id" discussion.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
