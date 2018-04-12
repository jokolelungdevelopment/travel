package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.MessagesService;
import com.jokolelung.travel.domain.Messages;
import com.jokolelung.travel.repository.MessagesRepository;
import com.jokolelung.travel.service.dto.MessagesDTO;
import com.jokolelung.travel.service.mapper.MessagesMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Messages.
 */
@Service
@Transactional
public class MessagesServiceImpl implements MessagesService {

    private final Logger log = LoggerFactory.getLogger(MessagesServiceImpl.class);

    private final MessagesRepository messagesRepository;

    private final MessagesMapper messagesMapper;

    public MessagesServiceImpl(MessagesRepository messagesRepository, MessagesMapper messagesMapper) {
        this.messagesRepository = messagesRepository;
        this.messagesMapper = messagesMapper;
    }

    /**
     * Save a messages.
     *
     * @param messagesDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public MessagesDTO save(MessagesDTO messagesDTO) {
        log.debug("Request to save Messages : {}", messagesDTO);
        Messages messages = messagesMapper.toEntity(messagesDTO);
        messages = messagesRepository.save(messages);
        return messagesMapper.toDto(messages);
    }

    /**
     * Get all the messages.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<MessagesDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Messages");
        return messagesRepository.findAll(pageable)
            .map(messagesMapper::toDto);
    }

    /**
     * Get one messages by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public MessagesDTO findOne(Long id) {
        log.debug("Request to get Messages : {}", id);
        Messages messages = messagesRepository.findOne(id);
        return messagesMapper.toDto(messages);
    }

    /**
     * Delete the messages by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Messages : {}", id);
        messagesRepository.delete(id);
    }
}
