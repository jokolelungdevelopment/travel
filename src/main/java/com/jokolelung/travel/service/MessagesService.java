package com.jokolelung.travel.service;

import com.jokolelung.travel.service.dto.MessagesDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Messages.
 */
public interface MessagesService {

    /**
     * Save a messages.
     *
     * @param messagesDTO the entity to save
     * @return the persisted entity
     */
    MessagesDTO save(MessagesDTO messagesDTO);

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<MessagesDTO> findAll(Pageable pageable);

    /**
     * Get the "id" messages.
     *
     * @param id the id of the entity
     * @return the entity
     */
    MessagesDTO findOne(Long id);

    /**
     * Delete the "id" messages.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
