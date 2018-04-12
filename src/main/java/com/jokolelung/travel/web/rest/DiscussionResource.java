package com.jokolelung.travel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jokolelung.travel.service.DiscussionService;
import com.jokolelung.travel.web.rest.errors.BadRequestAlertException;
import com.jokolelung.travel.web.rest.util.HeaderUtil;
import com.jokolelung.travel.web.rest.util.PaginationUtil;
import com.jokolelung.travel.service.dto.DiscussionDTO;
import io.github.jhipster.web.util.ResponseUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

/**
 * REST controller for managing Discussion.
 */
@RestController
@RequestMapping("/api")
public class DiscussionResource {

    private final Logger log = LoggerFactory.getLogger(DiscussionResource.class);

    private static final String ENTITY_NAME = "discussion";

    private final DiscussionService discussionService;

    public DiscussionResource(DiscussionService discussionService) {
        this.discussionService = discussionService;
    }

    /**
     * POST  /discussions : Create a new discussion.
     *
     * @param discussionDTO the discussionDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new discussionDTO, or with status 400 (Bad Request) if the discussion has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/discussions")
    @Timed
    public ResponseEntity<DiscussionDTO> createDiscussion(@RequestBody DiscussionDTO discussionDTO) throws URISyntaxException {
        log.debug("REST request to save Discussion : {}", discussionDTO);
        if (discussionDTO.getId() != null) {
            throw new BadRequestAlertException("A new discussion cannot already have an ID", ENTITY_NAME, "idexists");
        }
        DiscussionDTO result = discussionService.save(discussionDTO);
        return ResponseEntity.created(new URI("/api/discussions/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /discussions : Updates an existing discussion.
     *
     * @param discussionDTO the discussionDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated discussionDTO,
     * or with status 400 (Bad Request) if the discussionDTO is not valid,
     * or with status 500 (Internal Server Error) if the discussionDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/discussions")
    @Timed
    public ResponseEntity<DiscussionDTO> updateDiscussion(@RequestBody DiscussionDTO discussionDTO) throws URISyntaxException {
        log.debug("REST request to update Discussion : {}", discussionDTO);
        if (discussionDTO.getId() == null) {
            return createDiscussion(discussionDTO);
        }
        DiscussionDTO result = discussionService.save(discussionDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, discussionDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /discussions : get all the discussions.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of discussions in body
     */
    @GetMapping("/discussions")
    @Timed
    public ResponseEntity<List<DiscussionDTO>> getAllDiscussions(Pageable pageable) {
        log.debug("REST request to get a page of Discussions");
        Page<DiscussionDTO> page = discussionService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/discussions");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /discussions/:id : get the "id" discussion.
     *
     * @param id the id of the discussionDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the discussionDTO, or with status 404 (Not Found)
     */
    @GetMapping("/discussions/{id}")
    @Timed
    public ResponseEntity<DiscussionDTO> getDiscussion(@PathVariable Long id) {
        log.debug("REST request to get Discussion : {}", id);
        DiscussionDTO discussionDTO = discussionService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(discussionDTO));
    }

    /**
     * DELETE  /discussions/:id : delete the "id" discussion.
     *
     * @param id the id of the discussionDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/discussions/{id}")
    @Timed
    public ResponseEntity<Void> deleteDiscussion(@PathVariable Long id) {
        log.debug("REST request to delete Discussion : {}", id);
        discussionService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
