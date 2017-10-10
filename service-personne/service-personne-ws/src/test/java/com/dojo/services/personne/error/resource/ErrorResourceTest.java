package com.dojo.services.personne.error.resource;

import com.dojo.services.personne.error.exception.base.AbstractServiceException;

import org.junit.Before;
import org.junit.Test;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

/**
 * Created by sbert on 25/02/16.
 */
public class ErrorResourceTest {

    @Mock
    private WebRequest request;

    @Before
    public void setUp() {
        MockitoAnnotations.initMocks(this);
        when(request.getDescription(false)).thenReturn("uri:/test");

    }

    @Test
    public void check_complex_constructor() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        ErrorResource errorResource = new ErrorResource(new TestException("code", "defaultMessage"), HttpStatus.ACCEPTED, request);

        assertThat(errorResource.getCode()).isEqualTo("code");
        assertThat(errorResource.getError()).isEqualTo(HttpStatus.ACCEPTED.getReasonPhrase());
        assertThat(errorResource.getStatus()).isEqualTo(HttpStatus.ACCEPTED.value());
        assertThat(errorResource.getException()).isEqualTo("com.dojo.services.personne.error.resource.TestException");
        assertThat(errorResource.getPath()).isEqualTo("/test");
        assertThat(errorResource.getTimestamp()).isNotNull().isAfterOrEqualTo(zonedDateTime);
    }

    @Test
    public void check_simple_constructor() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();
        ErrorResource errorResource = new ErrorResource();

        assertThat(errorResource.getTimestamp()).isNotNull().isAfterOrEqualTo(zonedDateTime);
    }

    @Test
    public void check_getter_and_setter() {
        ZonedDateTime zonedDateTime = ZonedDateTime.now();

        List<FieldError> fieldErrors = new ArrayList<>();
        fieldErrors.add(new FieldError("objectName 1", "field 1", "default message 1"));
        fieldErrors.add(new FieldError("objectName 1", "field 2", "default message 2"));

        List<ObjectError> globalErrors = new ArrayList<>();
        globalErrors.add(new ObjectError("objectName 1", "default message 1"));
        globalErrors.add(new ObjectError("objectName 1", "default message 2"));

        ErrorResource errorResource = new ErrorResource();
        errorResource.setCode("code");
        errorResource.setError("error");
        errorResource.setException("exception");
        errorResource.setFieldErrors(fieldErrors);
        errorResource.setGlobalErrors(globalErrors);
        errorResource.setMessage("message");
        errorResource.setPath("path");
        errorResource.setStatus(200);

        assertThat(errorResource.getCode()).isEqualTo("code");
        assertThat(errorResource.getError()).isEqualTo("error");
        assertThat(errorResource.getException()).isEqualTo("exception");
        assertThat(errorResource.getFieldErrors())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting("objectName", "field", "defaultMessage").containsOnly(
                    tuple("objectName 1", "field 1", "default message 1"),
                    tuple("objectName 1", "field 2", "default message 2")
        );
        assertThat(errorResource.getGlobalErrors())
                .isNotNull()
                .isNotEmpty()
                .hasSize(2)
                .extracting("objectName", "defaultMessage").containsOnly(
                tuple("objectName 1", "default message 1"),
                tuple("objectName 1", "default message 2")
        );
        assertThat(errorResource.getMessage()).isEqualTo("message");
        assertThat(errorResource.getPath()).isEqualTo("path");
        assertThat(errorResource.getStatus()).isEqualTo(200);
        assertThat(errorResource.getTimestamp()).isNotNull().isAfterOrEqualTo(zonedDateTime);

    }
}

class TestException extends AbstractServiceException {

    public TestException(String code, String defaultMessage) {
        super(code, defaultMessage);
    }

    public TestException(String code, Object[] arguments, String defaultMessage) {
        super(code, arguments, defaultMessage);
    }

}
