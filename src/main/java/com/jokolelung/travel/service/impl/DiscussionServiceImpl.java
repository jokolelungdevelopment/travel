package com.jokolelung.travel.service.impl;

import com.jokolelung.travel.service.DiscussionService;
import com.jokolelung.travel.domain.Discussion;
import com.jokolelung.travel.repository.DiscussionRepository;
import com.jokolelung.travel.service.dto.DiscussionDTO;
import com.jokolelung.travel.service.mapper.DiscussionMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


/**
 * Service Implementation for managing Discussion.
 */
@Service
@Transactional
public class DiscussionServiceImpl implements DiscussionService {

    private final Logger log = LoggerFactory.getLogger(DiscussionServiceImpl.class);

    private final DiscussionRepository discussionRepository;

    private final DiscussionMapper discussionMapper;

    public DiscussionServiceImpl(DiscussionRepository discussionRepository, DiscussionMapper discussionMapper) {
        this.discussionRepository = discussionRepository;
        this.discussionMapper = discussionMapper;
    }

    /**
     * Save a discussion.
     *
     * @param discussionDTO the entity to save
     * @return the persisted entity
     */
    @Override
    public DiscussionDTO save(DiscussionDTO discussionDTO) {
        log.debug("Request to save Discussion : {}", discussionDTO);
        Discussion discussion = discussionMapper.toEntity(discussionDTO);
        discussion = discussionRepository.save(discussion);
        return discussionMapper.toDto(discussion);
    }

    /**
     * Get all the discussions.
     *
     * @param pageable the pagination information
     * @return the list of entities
     */
    @Override
    @Transactional(readOnly = true)
    public Page<DiscussionDTO> findAll(Pageable pageable) {
        log.debug("Request to get all Discussions");
        return discussionRepository.findAll(pageable)
            .map(discussionMapper::toDto);
    }

    /**
     * Get one discussion by id.
     *
     * @param id the id of the entity
     * @return the entity
     */
    @Override
    @Transactional(readOnly = true)
    public DiscussionDTO findOne(Long id) {
        log.debug("Request to get Discussion : {}", id);
        Discussion discussion = discussionRepository.findOne(id);
        return discussionMapper.toDto(discussion);
    }

    /**
     * Delete the discussion by id.
     *
     * @param id the id of the entity
     */
    @Override
    public void delete(Long id) {
        log.debug("Request to delete Discussion : {}", id);
        discussionRepository.delete(id);
    }
}
