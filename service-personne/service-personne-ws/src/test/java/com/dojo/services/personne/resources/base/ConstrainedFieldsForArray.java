package com.dojo.services.personne.resources.base;

import org.springframework.restdocs.constraints.ConstraintDescriptions;
import org.springframework.restdocs.payload.FieldDescriptor;
import org.springframework.util.StringUtils;

import static org.springframework.restdocs.payload.PayloadDocumentation.fieldWithPath;
import static org.springframework.restdocs.snippet.Attributes.key;

/**
 * Created by sbert on 13/06/16.
 */
public class ConstrainedFieldsForArray {

    private final ConstraintDescriptions constraintDescriptions;

    public ConstrainedFieldsForArray(Class<?> input) {
        this.constraintDescriptions = new ConstraintDescriptions(input);
    }

    public FieldDescriptor withPath(String path) {
        return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                .collectionToDelimitedString(this.constraintDescriptions
                        .descriptionsForProperty(path.substring(3)), ". ")));
    }

    public FieldDescriptor withPathWithAnotherConstraints(String path, String constraints) {
        return fieldWithPath(path).attributes(key("constraints").value(StringUtils
                .collectionToDelimitedString(this.constraintDescriptions
                        .descriptionsForProperty(path.substring(3)), "", "", ". ") + constraints
        ));
    }

}
