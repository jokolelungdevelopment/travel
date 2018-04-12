package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.PreOrderService;
import com.jokolelung.travel.domain.PreOrder;
import com.jokolelung.travel.repository.PreOrderRepository;
import com.jokolelung.travel.service.dto.PreOrderDTO;
import com.jokolelung.travel.service.mapper.PreOrderMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing PreOrder.
 */
@Service
@Transactional
public class PreOrderServiceImpl implements PreOrderService {

    private final Logger log = LoggerFactory.getLogger(PreOrderServiceImpl.class);

    private final PreOrderRepository preOrderRepository;

    private final PreOrderMapper preOrderMapper;

    public PreOrderServiceImpl(PreOrderRepository preOrderRepository, PreOrderMapper preOrderMapper) {
        this.preOrderRepository = preOrderRepository;
        this.preOrderMapper = preOrderMapper;
    }

    /**
     * Save a preOrder.
     *
     * @param preOrderDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public PreOrderDTO save(PreOrderDTO preOrderDTO) {
        log.debug("Request to save PreOrder : {}", preOrderDTO);
        PreOrder preOrder = preOrderMapper.toEntity(preOrderDTO);
        preOrder = preOrderRepository.save(preOrder);
        return preOrderMapper.toDto(preOrder);
    }

    /**
     * Get all the preOrders.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<PreOrderDTO> findAll(Pageable pageable) {
        log.debug("Request to get all PreOrders");
        return preOrderRepository.findAll(pageable)
            .map(preOrderMapper::toDto);
    }

    /**
     * Get one preOrder by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public PreOrderDTO findOne(Long id) {
        log.debug("Request to get PreOrder : {}", id);
        PreOrder preOrder = preOrderRepository.findOne(id);
        return preOrderMapper.toDto(preOrder);
    }

    /**
     * Delete the preOrder by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete PreOrder : {}", id);
        preOrderRepository.delete(id);
    }
}
