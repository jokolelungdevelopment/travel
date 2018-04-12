package com.jokolelung.travel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jokolelung.travel.service.InboxService;
import com.jokolelung.travel.web.rest.errors.BadRequestAlertException;
import com.jokolelung.travel.web.rest.util.HeaderUtil;
import com.jokolelung.travel.web.rest.util.PaginationUtil;
import com.jokolelung.travel.service.dto.InboxDTO;
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
 * REST controller for managing Inbox.
 */
@RestController
@RequestMapping("/api")
public class InboxResource {

    private final Logger log = LoggerFactory.getLogger(InboxResource.class);

    private static final String ENTITY_NAME = "inbox";

    private final InboxService inboxService;

    public InboxResource(InboxService inboxService) {
        this.inboxService = inboxService;
    }

    /**
     * POST  /inboxes : Create a new inbox.
     *
     * @param inboxDTO the inboxDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new inboxDTO, or with status 400 (Bad Request) if the inbox has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/inboxes")
    @Timed
    public ResponseEntity<InboxDTO> createInbox(@RequestBody InboxDTO inboxDTO) throws URISyntaxException {
        log.debug("REST request to save Inbox : {}", inboxDTO);
        if (inboxDTO.getId() != null) {
            throw new BadRequestAlertException("A new inbox cannot already have an ID", ENTITY_NAME, "idexists");
        }
        InboxDTO result = inboxService.save(inboxDTO);
        return ResponseEntity.created(new URI("/api/inboxes/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /inboxes : Updates an existing inbox.
     *
     * @param inboxDTO the inboxDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated inboxDTO,
     * or with status 400 (Bad Request) if the inboxDTO is not valid,
     * or with status 500 (Internal Server Error) if the inboxDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/inboxes")
    @Timed
    public ResponseEntity<InboxDTO> updateInbox(@RequestBody InboxDTO inboxDTO) throws URISyntaxException {
        log.debug("REST request to update Inbox : {}", inboxDTO);
        if (inboxDTO.getId() == null) {
            return createInbox(inboxDTO);
        }
        InboxDTO result = inboxService.save(inboxDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, inboxDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /inboxes : get all the inboxes.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of inboxes in body
     */
    @GetMapping("/inboxes")
    @Timed
    public ResponseEntity<List<InboxDTO>> getAllInboxes(Pageable pageable) {
        log.debug("REST request to get a page of Inboxes");
        Page<InboxDTO> page = inboxService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/inboxes");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /inboxes/:id : get the "id" inbox.
     *
     * @param id the id of the inboxDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the inboxDTO, or with status 404 (Not Found)
     */
    @GetMapping("/inboxes/{id}")
    @Timed
    public ResponseEntity<InboxDTO> getInbox(@PathVariable Long id) {
        log.debug("REST request to get Inbox : {}", id);
        InboxDTO inboxDTO = inboxService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(inboxDTO));
    }

    /**
     * DELETE  /inboxes/:id : delete the "id" inbox.
     *
     * @param id the id of the inboxDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/inboxes/{id}")
    @Timed
    public ResponseEntity<Void> deleteInbox(@PathVariable Long id) {
        log.debug("REST request to delete Inbox : {}", id);
        inboxService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
