package com.jokolelung.travel.service;

import com.jokolelung.travel.service.dto.InboxDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Inbox.
 */
public interface InboxService {

    /**
     * Save a inbox.
     *
     * @param inboxDTO the entity to save
     * @return the persisted entity
     */
    InboxDTO save(InboxDTO inboxDTO);

    /**
     * Get all the inboxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<InboxDTO> findAll(Pageable pageable);

    /**
     * Get the "id" inbox.
     *
     * @param id the id of the entity
     * @return the entity
     */
    InboxDTO findOne(Long id);

    /**
     * Delete the "id" inbox.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
