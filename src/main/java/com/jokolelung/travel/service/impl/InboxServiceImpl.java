package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.InboxService;
import com.jokolelung.travel.domain.Inbox;
import com.jokolelung.travel.repository.InboxRepository;
import com.jokolelung.travel.service.dto.InboxDTO;
import com.jokolelung.travel.service.mapper.InboxMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Inbox.
 */
@Service
@Transactional
public class InboxServiceImpl implements InboxService {

    private final Logger log = LoggerFactory.getLogger(InboxServiceImpl.class);

    private final InboxRepository inboxRepository;

    private final InboxMapper inboxMapper;

    public InboxServiceImpl(InboxRepository inboxRepository, InboxMapper inboxMapper) {
        this.inboxRepository = inboxRepository;
        this.inboxMapper = inboxMapper;
    }

    /**
     * Save a inbox.
     *
     * @param inboxDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public InboxDTO save(InboxDTO inboxDTO) {
        log.debug("Request to save Inbox : {}", inboxDTO);
        Inbox inbox = inboxMapper.toEntity(inboxDTO);
        inbox = inboxRepository.save(inbox);
        return inboxMapper.toDto(inbox);
    }

    /**
     * Get all the inboxes.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<InboxDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Inboxes");
        return inboxRepository.findAll(pageable)
            .map(inboxMapper::toDto);
    }

    /**
     * Get one inbox by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public InboxDTO findOne(Long id) {
        log.debug("Request to get Inbox : {}", id);
        Inbox inbox = inboxRepository.findOne(id);
        return inboxMapper.toDto(inbox);
    }

    /**
     * Delete the inbox by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Inbox : {}", id);
        inboxRepository.delete(id);
    }
}
