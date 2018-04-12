package com.jokolelung.travel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jokolelung.travel.service.PreOrderService;
import com.jokolelung.travel.web.rest.errors.BadRequestAlertException;
import com.jokolelung.travel.web.rest.util.HeaderUtil;
import com.jokolelung.travel.web.rest.util.PaginationUtil;
import com.jokolelung.travel.service.dto.PreOrderDTO;
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
 * REST controller for managing PreOrder.
 */
@RestController
@RequestMapping("/api")
public class PreOrderResource {

    private final Logger log = LoggerFactory.getLogger(PreOrderResource.class);

    private static final String ENTITY_NAME = "preOrder";

    private final PreOrderService preOrderService;

    public PreOrderResource(PreOrderService preOrderService) {
        this.preOrderService = preOrderService;
    }

    /**
     * POST  /pre-orders : Create a new preOrder.
     *
     * @param preOrderDTO the preOrderDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new preOrderDTO, or with status 400 (Bad Request) if the preOrder has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/pre-orders")
    @Timed
    public ResponseEntity<PreOrderDTO> createPreOrder(@RequestBody PreOrderDTO preOrderDTO) throws URISyntaxException {
        log.debug("REST request to save PreOrder : {}", preOrderDTO);
        if (preOrderDTO.getId() != null) {
            throw new BadRequestAlertException("A new preOrder cannot already have an ID", ENTITY_NAME, "idexists");
        }
        PreOrderDTO result = preOrderService.save(preOrderDTO);
        return ResponseEntity.created(new URI("/api/pre-orders/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /pre-orders : Updates an existing preOrder.
     *
     * @param preOrderDTO the preOrderDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated preOrderDTO,
     * or with status 400 (Bad Request) if the preOrderDTO is not valid,
     * or with status 500 (Internal Server Error) if the preOrderDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/pre-orders")
    @Timed
    public ResponseEntity<PreOrderDTO> updatePreOrder(@RequestBody PreOrderDTO preOrderDTO) throws URISyntaxException {
        log.debug("REST request to update PreOrder : {}", preOrderDTO);
        if (preOrderDTO.getId() == null) {
            return createPreOrder(preOrderDTO);
        }
        PreOrderDTO result = preOrderService.save(preOrderDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, preOrderDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /pre-orders : get all the preOrders.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of preOrders in body
     */
    @GetMapping("/pre-orders")
    @Timed
    public ResponseEntity<List<PreOrderDTO>> getAllPreOrders(Pageable pageable) {
        log.debug("REST request to get a page of PreOrders");
        Page<PreOrderDTO> page = preOrderService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/pre-orders");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /pre-orders/:id : get the "id" preOrder.
     *
     * @param id the id of the preOrderDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the preOrderDTO, or with status 404 (Not Found)
     */
    @GetMapping("/pre-orders/{id}")
    @Timed
    public ResponseEntity<PreOrderDTO> getPreOrder(@PathVariable Long id) {
        log.debug("REST request to get PreOrder : {}", id);
        PreOrderDTO preOrderDTO = preOrderService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(preOrderDTO));
    }

    /**
     * DELETE  /pre-orders/:id : delete the "id" preOrder.
     *
     * @param id the id of the preOrderDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/pre-orders/{id}")
    @Timed
    public ResponseEntity<Void> deletePreOrder(@PathVariable Long id) {
        log.debug("REST request to delete PreOrder : {}", id);
        preOrderService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
