package com.jokolelung.travel.service;

import com.jokolelung.travel.service.dto.OfferDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

/**
 * Service Interface for managing Offer.
 */
public interface OfferService {

    /**
     * Save a offer.
     *
     * @param offerDTO the entity to save
     * @return the persisted entity
     */
    OfferDTO save(OfferDTO offerDTO);

    /**
     * Get all the offers.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    Page<OfferDTO> findAll(Pageable pageable);

    /**
     * Get the "id" offer.
     *
     * @param id the id of the entity
     * @return the entity
     */
    OfferDTO findOne(Long id);

    /**
     * Delete the "id" offer.
     *
     * @param id the id of the entity
     */
    void delete(Long id);
}
