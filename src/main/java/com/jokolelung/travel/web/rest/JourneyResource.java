package com.jokolelung.travel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jokolelung.travel.service.JourneyService;
import com.jokolelung.travel.web.rest.errors.BadRequestAlertException;
import com.jokolelung.travel.web.rest.util.HeaderUtil;
import com.jokolelung.travel.web.rest.util.PaginationUtil;
import com.jokolelung.travel.service.dto.JourneyDTO;
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
 * REST controller for managing Journey.
 */
@RestController
@RequestMapping("/api")
public class JourneyResource {

    private final Logger log = LoggerFactory.getLogger(JourneyResource.class);

    private static final String ENTITY_NAME = "journey";

    private final JourneyService journeyService;

    public JourneyResource(JourneyService journeyService) {
        this.journeyService = journeyService;
    }

    /**
     * POST  /journeys : Create a new journey.
     *
     * @param journeyDTO the journeyDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new journeyDTO, or with status 400 (Bad Request) if the journey has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/journeys")
    @Timed
    public ResponseEntity<JourneyDTO> createJourney(@RequestBody JourneyDTO journeyDTO) throws URISyntaxException {
        log.debug("REST request to save Journey : {}", journeyDTO);
        if (journeyDTO.getId() != null) {
            throw new BadRequestAlertException("A new journey cannot already have an ID", ENTITY_NAME, "idexists");
        }
        JourneyDTO result = journeyService.save(journeyDTO);
        return ResponseEntity.created(new URI("/api/journeys/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /journeys : Updates an existing journey.
     *
     * @param journeyDTO the journeyDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated journeyDTO,
     * or with status 400 (Bad Request) if the journeyDTO is not valid,
     * or with status 500 (Internal Server Error) if the journeyDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/journeys")
    @Timed
    public ResponseEntity<JourneyDTO> updateJourney(@RequestBody JourneyDTO journeyDTO) throws URISyntaxException {
        log.debug("REST request to update Journey : {}", journeyDTO);
        if (journeyDTO.getId() == null) {
            return createJourney(journeyDTO);
        }
        JourneyDTO result = journeyService.save(journeyDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, journeyDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /journeys : get all the journeys.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of journeys in body
     */
    @GetMapping("/journeys")
    @Timed
    public ResponseEntity<List<JourneyDTO>> getAllJourneys(Pageable pageable) {
        log.debug("REST request to get a page of Journeys");
        Page<JourneyDTO> page = journeyService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/journeys");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /journeys/:id : get the "id" journey.
     *
     * @param id the id of the journeyDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the journeyDTO, or with status 404 (Not Found)
     */
    @GetMapping("/journeys/{id}")
    @Timed
    public ResponseEntity<JourneyDTO> getJourney(@PathVariable Long id) {
        log.debug("REST request to get Journey : {}", id);
        JourneyDTO journeyDTO = journeyService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(journeyDTO));
    }

    /**
     * DELETE  /journeys/:id : delete the "id" journey.
     *
     * @param id the id of the journeyDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/journeys/{id}")
    @Timed
    public ResponseEntity<Void> deleteJourney(@PathVariable Long id) {
        log.debug("REST request to delete Journey : {}", id);
        journeyService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
