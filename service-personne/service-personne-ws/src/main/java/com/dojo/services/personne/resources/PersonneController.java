package com.dojo.services.personne.resources;

import com.codahale.metrics.annotation.Timed;

import com.dojo.services.personne.model.entity.Personne;
import com.dojo.services.personne.repository.PersonneRepository;
import com.dojo.services.personne.error.exception.InvalidRequestException;
import com.dojo.services.personne.error.exception.ResourceNotFoundException;

import io.swagger.annotations.ApiOperation;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.springframework.web.bind.annotation.*;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.BindingResult;

import javax.annotation.Resource;
import javax.validation.Valid;

import java.net.URI;
import java.net.URISyntaxException;

import java.util.List;
import java.util.Optional;

import static com.dojo.services.personne.error.code.PersonneErrorCode.*;

@RestController
@RequestMapping(value = "/personnes", produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
public class PersonneController {

    private static final Logger LOGGER = LoggerFactory.getLogger(PersonneController.class);
    
    @Resource
    private PersonneRepository personneRepository;

    /**
     * GET  /personnes : get all the personnes.
     */
    @ApiOperation(value = "Get all the personnes", notes = "Get all the personnes")
    @Timed
    @GetMapping
    public List<Personne> getAll() {
        LOGGER.debug("REST request to get all Personnes");
        return personneRepository.findAll();
    }


    /**
     * GET  /personnes/:id : get the "id" personne.
     */
    @ApiOperation(value = "Get a personne by its Id", notes = "Get a personne by its Id")
    @Timed
    @GetMapping(value = "/{id}")
    public ResponseEntity<Personne> get(@PathVariable Long id) {
        LOGGER.debug("REST request to get Personne : {}", id);
        return Optional.ofNullable(personneRepository.findOne(id))
                .map(personne -> new ResponseEntity<>(personne, HttpStatus.OK))
                .orElseThrow(() -> new ResourceNotFoundException(RESOURCE_NOT_FOUND_BY_ID, "personne with id: " + id + " was not found"));
    }

    /**
     * POST  /personnes : Create a new personne.
     */
    @ApiOperation(value = "Create a new personne", notes = "Create a new personne")
    @Timed
    @PostMapping
    public ResponseEntity<Void> create(@Valid @RequestBody Personne personne, BindingResult bindingResult) throws URISyntaxException {
        LOGGER.debug("REST request to create Personne : {}", personne);
        if (personne.getId() != null) {
            bindingResult.rejectValue("id", RESOURCE_CREATE_WITH_ID, "A new personne cannot already have an ID");
        }
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(RESOURCE_INVALID, "Invalid Personne", bindingResult);
        }
        personneRepository.save(personne);
        return ResponseEntity.created(new URI("/personnes/" + personne.getId())).build();
    }

    /**
     * PUT  /personnes : Update an existing personne.
     */
    @ApiOperation(value = "Update an existing personne", notes = "Update an existing personne")
    @Timed
    @PutMapping
    public ResponseEntity<Void> update(@Valid @RequestBody Personne personne, BindingResult bindingResult) throws URISyntaxException {
        LOGGER.debug("REST request to update Personne : {}", personne);
        if (personne.getId() == null) {
            bindingResult.rejectValue("id", RESOURCE_ID_INVALID, "An existing personne must have a valid ID");
        } else {
            if (null == personneRepository.findOne(personne.getId())) {
                throw new ResourceNotFoundException(RESOURCE_NOT_FOUND_BY_ID, "The existing personne was not found");
            }

        }
        if (bindingResult.hasErrors()) {
            throw new InvalidRequestException(RESOURCE_INVALID, "Invalid Personne", bindingResult);
        }
        personneRepository.save(personne);
        return ResponseEntity.ok().build();
    }

    /**
     * DELETE  /personnes/:id : Delete the "id" personne.
     */
    @ApiOperation(value = "Delete a personne by its Id", notes = "Delete a personne by its Id")
    @Timed
    @DeleteMapping(value = "/{id}")
    public void delete(@PathVariable Long id) {
        LOGGER.debug("REST request to delete Personne : {}", id);
        personneRepository.delete(id);
    }

}
