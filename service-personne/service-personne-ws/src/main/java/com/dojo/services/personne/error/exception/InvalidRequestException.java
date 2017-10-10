package com.dojo.services.personne.error.exception;

import com.dojo.services.personne.error.exception.base.AbstractServiceException;

import org.springframework.validation.Errors;

public class InvalidRequestException extends AbstractServiceException {

    private Errors errors;

    public InvalidRequestException(String code, String defaultMessage, Errors errors) {
        super(code, defaultMessage);
        this.errors = errors;
    }

    public InvalidRequestException(String code, Object[] arguments, String defaultMessage, Errors errors) {
        super (code, arguments, defaultMessage);
        this.errors = errors;
    }

    public Errors getErrors() {
        return errors;
    }
}
