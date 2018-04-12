package com.jokolelung.travel.service;

import com.jokolelung.travel.service.dto.PreOrderDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing PreOrder.
 */
public interface PreOrderService {

    /**
     * Save a preOrder.
     *
     * @param preOrderDTO the entity to save
     * @return the persisted entity
     */
    PreOrderDTO save(PreOrderDTO preOrderDTO);

    /**
     * Get all the preOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<PreOrderDTO> findAll(Pageable pageable);

    /**
     * Get the "id" preOrder.
     *
     * @param id the id of the entity
     * @return the entity
     */
    PreOrderDTO findOne(Long id);

    /**
     * Delete the "id" preOrder.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
