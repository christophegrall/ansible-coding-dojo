package com.dojo.services.personne.error.exception;

import com.dojo.services.personne.error.exception.base.AbstractServiceException;

public class ResourceNotFoundException extends AbstractServiceException {

    public ResourceNotFoundException(String code, String defaultMessage) {
        super(code, defaultMessage);
    }

    public ResourceNotFoundException(String code, Object[] arguments, String defaultMessage) {
        super(code, arguments, defaultMessage);
    }

}
