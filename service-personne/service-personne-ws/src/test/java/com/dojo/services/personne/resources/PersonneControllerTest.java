package com.dojo.services.personne.resources;

import com.dojo.services.personne.model.entity.Personne;
import com.dojo.services.personne.repository.PersonneRepository;
import com.dojo.services.personne.resources.base.AbstractControllerTest;
import com.dojo.services.personne.resources.base.ConstrainedFields;
import com.dojo.services.personne.resources.base.ConstrainedFieldsForArray;
import com.dojo.services.personne.util.TestUtil;

import org.junit.Before;
import org.junit.Test;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.json.MappingJackson2HttpMessageConverter;
import org.springframework.data.web.PageableHandlerMethodArgumentResolver;
import org.springframework.restdocs.mockmvc.RestDocumentationRequestBuilders;
import org.springframework.restdocs.mockmvc.RestDocumentationResultHandler;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.restdocs.payload.JsonFieldType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;


import static org.assertj.core.api.Assertions.assertThat;

import static org.springframework.restdocs.request.RequestDocumentation.parameterWithName;
import static org.springframework.restdocs.request.RequestDocumentation.pathParameters;
import static org.springframework.restdocs.request.RequestDocumentation.requestParameters;
import static org.springframework.restdocs.snippet.Attributes.key;
import static org.springframework.restdocs.payload.PayloadDocumentation.requestFields;
import static org.springframework.restdocs.payload.PayloadDocumentation.responseFields;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

import static org.hamcrest.Matchers.hasItem;

/**
 * Test class for the PersonneController REST controller.
 */
public class PersonneControllerTest extends AbstractControllerTest {

    
    private static final String DEFAULT_NOM = "AAAAA";
    private static final String UPDATED_NOM = "BBBBB";


    private static final String DEFAULT_PRENOM = "AAAAA";
    private static final String UPDATED_PRENOM = "BBBBB";


    @Resource
    private PersonneRepository personneRepository;

    @Resource
    private MappingJackson2HttpMessageConverter jacksonMessageConverter;

    @Resource
    private PageableHandlerMethodArgumentResolver pageableArgumentResolver;

    @Autowired
    private MockMvc restPersonneMockMvc;

    @Autowired
    private RestDocumentationResultHandler documentationHandler;

    private Personne personne;

    @Before
    public void initTest() {

        personne = new Personne();
        personne.setNom(DEFAULT_NOM);
        personne.setPrenom(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void create_personne() throws Exception {
        int databaseSizeBeforeCreate = personneRepository.findAll().size();

        // Create the Personne
        restPersonneMockMvc.perform(post("/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isCreated())
                .andDo(documentationHandler.document(requestFields(PersonneFieldDescriptors.get())))
        ;

        // Validate the Personne in the database
        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeCreate + 1);
        Personne testPersonne = personnes.get(personnes.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(DEFAULT_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(DEFAULT_PRENOM);
    }

    @Test
    @Transactional
    public void create_personne_with_an_id_should_return_bad_request_error() throws Exception {
        personne.setId(1L);
        // Trying to create the Personne
        restPersonneMockMvc.perform(post("/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.resource.invalid"))
                .andExpect(jsonPath("$.message").value("Invalid Personne"))
                .andExpect(jsonPath("$.path").value("/personnes"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.InvalidRequestException"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors..objectName").value("personne"))
                .andExpect(jsonPath("$.fieldErrors..field").value("id"))
                .andExpect(jsonPath("$.fieldErrors..code").value("error.resource.create.with.id"))
                .andExpect(jsonPath("$.fieldErrors..rejectedValue").value(1))
                .andExpect(jsonPath("$.fieldErrors..message").value("A new personne cannot already have an ID"));
    }
    
    @Test
    @Transactional
    public void get_all_personnes() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        Personne personne2 = new Personne();
        personne2.setNom(UPDATED_NOM);
        personne2.setPrenom(UPDATED_PRENOM);
        personneRepository.saveAndFlush(personne2);

        ConstrainedFieldsForArray fields = new ConstrainedFieldsForArray(Personne.class);

        List<FieldDescriptor> fieldDescriptorsForArray = new ArrayList<>();
        fieldDescriptorsForArray.add(fields.withPath("[].id").description("L'identifiant technique").attributes(key("constraints").value("Must not be null")));
        fieldDescriptorsForArray.addAll(PersonneFieldDescriptors.getForArray());

        // Get all the personnes
        restPersonneMockMvc.perform(get("/personnes"))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.[*].id").value(hasItem(personne.getId().intValue())))
                .andExpect(jsonPath("$.[*].nom").value(hasItem(DEFAULT_NOM.toString())))
                .andExpect(jsonPath("$.[*].prenom").value(hasItem(DEFAULT_PRENOM.toString())))
                .andDo(documentationHandler.document(responseFields(fieldDescriptorsForArray)))
        ;
    }

    @Test
    @Transactional
    public void get_personne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        ConstrainedFields fields = new ConstrainedFields(Personne.class);

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(fields.withPath("id").description("L'identifiant technique").attributes(key("constraints").value("Must not be null")));
        fieldDescriptors.addAll(PersonneFieldDescriptors.get());

        // Get the personne
        restPersonneMockMvc.perform(RestDocumentationRequestBuilders.get("/personnes/{id}", personne.getId()))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.id").value(personne.getId().intValue()))
                .andExpect(jsonPath("$.nom").value(DEFAULT_NOM.toString()))
                .andExpect(jsonPath("$.prenom").value(DEFAULT_PRENOM.toString()))
                .andDo(documentationHandler.document(
                        responseFields(fieldDescriptors),
                        pathParameters(parameterWithName("id")
                                .description("L'identifiant technique")
                                .attributes(key("constraints").value("")))
                ))
        ;
    }

    @Test
    @Transactional
    public void get_non_existing_personne() throws Exception {
        // Get the personne
        restPersonneMockMvc.perform(get("/personnes/{id}", Long.MAX_VALUE))
            .andExpect(status().isNotFound())
            .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
            .andExpect(jsonPath("$.timestamp").isNotEmpty())
            .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
            .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
            .andExpect(jsonPath("$.code").value("error.resource.not.found.by.id"))
            .andExpect(jsonPath("$.message").value("personne with id: " + Long.MAX_VALUE + " was not found"))
            .andExpect(jsonPath("$.path").value("/personnes/" + Long.MAX_VALUE))
            .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.ResourceNotFoundException"))
            .andExpect(jsonPath("$.globalErrors").doesNotExist())
            .andExpect(jsonPath("$.fieldErrors").doesNotExist());
    }

    @Test
    @Transactional
    public void update_personne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

		int databaseSizeBeforeUpdate = personneRepository.findAll().size();

        // Update the personne
        personne.setNom(UPDATED_NOM);
        personne.setPrenom(UPDATED_PRENOM);

        ConstrainedFields fields = new ConstrainedFields(Personne.class);

        List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
        fieldDescriptors.add(
                fields.withPath("id")
                        .description("L'identifiant technique")
                        .attributes(key("constraints").value("Must not be null"))
                        .attributes(key("required").value("x"))
        );
        fieldDescriptors.addAll(PersonneFieldDescriptors.get());

        restPersonneMockMvc.perform(put("/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isOk())
                .andDo(documentationHandler.document(requestFields(fieldDescriptors)))
        ;

        // Validate the Personne in the database
        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeUpdate);
        Personne testPersonne = personnes.get(personnes.size() - 1);
        assertThat(testPersonne.getNom()).isEqualTo(UPDATED_NOM);
        assertThat(testPersonne.getPrenom()).isEqualTo(UPDATED_PRENOM);
    }

    @Test
    @Transactional
    public void update_personne_with_an_id_null_should_return_bad_request_error() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        //Set Id to null
        personne.setId(null);

        restPersonneMockMvc.perform(put("/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.resource.invalid"))
                .andExpect(jsonPath("$.message").value("Invalid Personne"))
                .andExpect(jsonPath("$.path").value("/personnes"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.InvalidRequestException"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors..objectName").value("personne"))
                .andExpect(jsonPath("$.fieldErrors..field").value("id"))
                .andExpect(jsonPath("$.fieldErrors..code").value("error.resource.id.invalid"))
                .andExpect(jsonPath("$.fieldErrors..rejectedValue").exists())
                .andExpect(jsonPath("$.fieldErrors..message").value("An existing personne must have a valid ID"));
    }

    @Test
    @Transactional
    public void update_a_non_existing_personne_should_return_a_not_found_error() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

        //Set a non existing id (I hope)
        personne.setId(Long.MAX_VALUE);

        restPersonneMockMvc.perform(put("/personnes")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(personne)))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.resource.not.found.by.id"))
                .andExpect(jsonPath("$.message").value("The existing personne was not found"))
                .andExpect(jsonPath("$.path").value("/personnes"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.ResourceNotFoundException"))
                .andExpect(jsonPath("$.globalErrors").doesNotExist())
                .andExpect(jsonPath("$.fieldErrors").doesNotExist());
    }
    
    @Test
    @Transactional
    public void delete_personne() throws Exception {
        // Initialize the database
        personneRepository.saveAndFlush(personne);

		int databaseSizeBeforeDelete = personneRepository.findAll().size();

        // Get the personne
        restPersonneMockMvc.perform(RestDocumentationRequestBuilders.delete("/personnes/{id}", personne.getId())
                .accept(TestUtil.APPLICATION_JSON_UTF8))
                .andExpect(status().isOk())
                .andDo(documentationHandler.document(
                        pathParameters(parameterWithName("id")
                                .description("L'identifiant technique")
                                .attributes(key("constraints").value("Must not be Null or Empty")))
                ))
        ;

        // Validate the database is empty
        List<Personne> personnes = personneRepository.findAll();
        assertThat(personnes).hasSize(databaseSizeBeforeDelete - 1);
    }

    public static class PersonneFieldDescriptors {

        public static List<FieldDescriptor> get() {

            ConstrainedFields fields = new ConstrainedFields(Personne.class);

            List<FieldDescriptor> fieldDescriptors = new ArrayList<>();
            fieldDescriptors.add(fields.withPath("nom").description("nom").type(JsonFieldType.STRING).optional().attributes(key("required").value("")));
            fieldDescriptors.add(fields.withPath("prenom").description("prenom").type(JsonFieldType.STRING).optional().attributes(key("required").value("")));

            return fieldDescriptors;
        }

        public static List<FieldDescriptor> getForArray() {

            ConstrainedFieldsForArray fields = new ConstrainedFieldsForArray(Personne.class);

            List<FieldDescriptor> fieldDescriptorsForArray = new ArrayList<>();
            fieldDescriptorsForArray.add(fields.withPath("[].nom").description("nom").type(JsonFieldType.STRING).optional());
            fieldDescriptorsForArray.add(fields.withPath("[].prenom").description("prenom").type(JsonFieldType.STRING).optional());

            return fieldDescriptorsForArray;
        }

    }
}
