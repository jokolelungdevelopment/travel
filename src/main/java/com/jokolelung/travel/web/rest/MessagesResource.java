package com.jokolelung.travel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jokolelung.travel.service.MessagesService;
import com.jokolelung.travel.web.rest.errors.BadRequestAlertException;
import com.jokolelung.travel.web.rest.util.HeaderUtil;
import com.jokolelung.travel.web.rest.util.PaginationUtil;
import com.jokolelung.travel.service.dto.MessagesDTO;
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
 * REST controller for managing Messages.
 */
@RestController
@RequestMapping("/api")
public class MessagesResource {

    private final Logger log = LoggerFactory.getLogger(MessagesResource.class);

    private static final String ENTITY_NAME = "messages";

    private final MessagesService messagesService;

    public MessagesResource(MessagesService messagesService) {
        this.messagesService = messagesService;
    }

    /**
     * POST  /messages : Create a new messages.
     *
     * @param messagesDTO the messagesDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new messagesDTO, or with status 400 (Bad Request) if the messages has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/messages")
    @Timed
    public ResponseEntity<MessagesDTO> createMessages(@RequestBody MessagesDTO messagesDTO) throws URISyntaxException {
        log.debug("REST request to save Messages : {}", messagesDTO);
        if (messagesDTO.getId() != null) {
            throw new BadRequestAlertException("A new messages cannot already have an ID", ENTITY_NAME, "idexists");
        }
        MessagesDTO result = messagesService.save(messagesDTO);
        return ResponseEntity.created(new URI("/api/messages/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /messages : Updates an existing messages.
     *
     * @param messagesDTO the messagesDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated messagesDTO,
     * or with status 400 (Bad Request) if the messagesDTO is not valid,
     * or with status 500 (Internal Server Error) if the messagesDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/messages")
    @Timed
    public ResponseEntity<MessagesDTO> updateMessages(@RequestBody MessagesDTO messagesDTO) throws URISyntaxException {
        log.debug("REST request to update Messages : {}", messagesDTO);
        if (messagesDTO.getId() == null) {
            return createMessages(messagesDTO);
        }
        MessagesDTO result = messagesService.save(messagesDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, messagesDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /messages : get all the messages.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of messages in body
     */
    @GetMapping("/messages")
    @Timed
    public ResponseEntity<List<MessagesDTO>> getAllMessages(Pageable pageable) {
        log.debug("REST request to get a page of Messages");
        Page<MessagesDTO> page = messagesService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/messages");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /messages/:id : get the "id" messages.
     *
     * @param id the id of the messagesDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the messagesDTO, or with status 404 (Not Found)
     */
    @GetMapping("/messages/{id}")
    @Timed
    public ResponseEntity<MessagesDTO> getMessages(@PathVariable Long id) {
        log.debug("REST request to get Messages : {}", id);
        MessagesDTO messagesDTO = messagesService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(messagesDTO));
    }

    /**
     * DELETE  /messages/:id : delete the "id" messages.
     *
     * @param id the id of the messagesDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/messages/{id}")
    @Timed
    public ResponseEntity<Void> deleteMessages(@PathVariable Long id) {
        log.debug("REST request to delete Messages : {}", id);
        messagesService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
