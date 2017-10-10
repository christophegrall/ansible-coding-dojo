package com.dojo.services.personne.model.entity;

import nl.jqno.equalsverifier.EqualsVerifier;
import nl.jqno.equalsverifier.Warning;

import org.junit.Before;
import org.junit.Test;



import static org.assertj.core.api.Assertions.assertThat;

public class PersonneTest {

    private static final Long DEFAULT_ID = 1L;
    
    private static final String DEFAULT_NOM = "AAAAA";


    private static final String DEFAULT_PRENOM = "AAAAA";


    private Personne personne;

    @Before
    public void initTest() {

        personne = new Personne();
        personne.setId(DEFAULT_ID);
        personne.setNom(DEFAULT_NOM);
        personne.setPrenom(DEFAULT_PRENOM);
    }

    @Test
    public void checkEqualsPersonne() {

        EqualsVerifier.forClass(Personne.class)
                .suppress(Warning.STRICT_INHERITANCE)
                .suppress(Warning.IDENTICAL_COPY_FOR_VERSIONED_ENTITY)
                .verify();
    }

    @Test
    public void checkToString() {
        String expected = "Personne{" +
                "id=" + DEFAULT_ID +
                ", nom='" + DEFAULT_NOM + "'" +
                ", prenom='" + DEFAULT_PRENOM + "'" +
                '}';
        assertThat(personne.toString()).isEqualTo(expected);
    }



}
