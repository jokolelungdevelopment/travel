package com.jokolelung.travel.web.rest;

import com.codahale.metrics.annotation.Timed;
import com.jokolelung.travel.service.SlideService;
import com.jokolelung.travel.web.rest.errors.BadRequestAlertException;
import com.jokolelung.travel.web.rest.util.HeaderUtil;
import com.jokolelung.travel.web.rest.util.PaginationUtil;
import com.jokolelung.travel.service.dto.SlideDTO;
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
 * REST controller for managing Slide.
 */
@RestController
@RequestMapping("/api")
public class SlideResource {

    private final Logger log = LoggerFactory.getLogger(SlideResource.class);

    private static final String ENTITY_NAME = "slide";

    private final SlideService slideService;

    public SlideResource(SlideService slideService) {
        this.slideService = slideService;
    }

    /**
     * POST  /slides : Create a new slide.
     *
     * @param slideDTO the slideDTO to create
     * @return the ResponseEntity with status 201 (Created) and with body the new slideDTO, or with status 400 (Bad Request) if the slide has already an ID
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PostMapping("/slides")
    @Timed
    public ResponseEntity<SlideDTO> createSlide(@RequestBody SlideDTO slideDTO) throws URISyntaxException {
        log.debug("REST request to save Slide : {}", slideDTO);
        if (slideDTO.getId() != null) {
            throw new BadRequestAlertException("A new slide cannot already have an ID", ENTITY_NAME, "idexists");
        }
        SlideDTO result = slideService.save(slideDTO);
        return ResponseEntity.created(new URI("/api/slides/" + result.getId()))
            .headers(HeaderUtil.createEntityCreationAlert(ENTITY_NAME, result.getId().toString()))
            .body(result);
    }

    /**
     * PUT  /slides : Updates an existing slide.
     *
     * @param slideDTO the slideDTO to update
     * @return the ResponseEntity with status 200 (OK) and with body the updated slideDTO,
     * or with status 400 (Bad Request) if the slideDTO is not valid,
     * or with status 500 (Internal Server Error) if the slideDTO couldn't be updated
     * @throws URISyntaxException if the Location URI syntax is incorrect
     */
    @PutMapping("/slides")
    @Timed
    public ResponseEntity<SlideDTO> updateSlide(@RequestBody SlideDTO slideDTO) throws URISyntaxException {
        log.debug("REST request to update Slide : {}", slideDTO);
        if (slideDTO.getId() == null) {
            return createSlide(slideDTO);
        }
        SlideDTO result = slideService.save(slideDTO);
        return ResponseEntity.ok()
            .headers(HeaderUtil.createEntityUpdateAlert(ENTITY_NAME, slideDTO.getId().toString()))
            .body(result);
    }

    /**
     * GET  /slides : get all the slides.
     *
     * @param pageable the pagination information
     * @return the ResponseEntity with status 200 (OK) and the list of slides in body
     */
    @GetMapping("/slides")
    @Timed
    public ResponseEntity<List<SlideDTO>> getAllSlides(Pageable pageable) {
        log.debug("REST request to get a page of Slides");
        Page<SlideDTO> page = slideService.findAll(pageable);
        HttpHeaders headers = PaginationUtil.generatePaginationHttpHeaders(page, "/api/slides");
        return new ResponseEntity<>(page.getContent(), headers, HttpStatus.OK);
    }

    /**
     * GET  /slides/:id : get the "id" slide.
     *
     * @param id the id of the slideDTO to retrieve
     * @return the ResponseEntity with status 200 (OK) and with body the slideDTO, or with status 404 (Not Found)
     */
    @GetMapping("/slides/{id}")
    @Timed
    public ResponseEntity<SlideDTO> getSlide(@PathVariable Long id) {
        log.debug("REST request to get Slide : {}", id);
        SlideDTO slideDTO = slideService.findOne(id);
        return ResponseUtil.wrapOrNotFound(Optional.ofNullable(slideDTO));
    }

    /**
     * DELETE  /slides/:id : delete the "id" slide.
     *
     * @param id the id of the slideDTO to delete
     * @return the ResponseEntity with status 200 (OK)
     */
    @DeleteMapping("/slides/{id}")
    @Timed
    public ResponseEntity<Void> deleteSlide(@PathVariable Long id) {
        log.debug("REST request to delete Slide : {}", id);
        slideService.delete(id);
        return ResponseEntity.ok().headers(HeaderUtil.createEntityDeletionAlert(ENTITY_NAME, id.toString())).build();
    }
}
