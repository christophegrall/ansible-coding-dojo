package com.dojo.services.personne.error.handler;

import com.dojo.services.personne.error.resource.ErrorResource;
import com.dojo.services.personne.error.exception.InvalidRequestException;
import com.dojo.services.personne.error.exception.ResourceNotFoundException;

import org.springframework.context.MessageSource;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import javax.annotation.Resource;

/**
 * Global Exception Handler
 */
@ControllerAdvice
public class GlobalControllerExceptionHandler  extends ResponseEntityExceptionHandler {

    @Resource
    MessageSource messageSource;

    @ExceptionHandler(value = { ResourceNotFoundException.class})
    protected ResponseEntity<Object> handleResourceNotFoundException(RuntimeException e, WebRequest request) {
        ResourceNotFoundException rnfe = (ResourceNotFoundException) e;

        String message = messageSource.getMessage(rnfe.getCode(), rnfe.getArguments(), rnfe.getMessage(), null);

        ErrorResource errorResource = new ErrorResource(rnfe, HttpStatus.NOT_FOUND, request);
        errorResource.setMessage(message);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return handleExceptionInternal(e, errorResource, headers, HttpStatus.NOT_FOUND, request);
    }

    @ExceptionHandler(value = { InvalidRequestException.class })
    protected ResponseEntity<Object> handleInvalidRequestException(RuntimeException e, WebRequest request) {
        InvalidRequestException ire = (InvalidRequestException) e;

        String message = messageSource.getMessage(ire.getCode(), ire.getArguments(), ire.getMessage(), null);

        ErrorResource errorResource = new ErrorResource(ire, HttpStatus.BAD_REQUEST, request);
        errorResource.setMessage(message);
        errorResource.setGlobalErrors(ire.getErrors().getGlobalErrors());
        errorResource.setFieldErrors(ire.getErrors().getFieldErrors());

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_JSON_UTF8);

        return handleExceptionInternal(e, errorResource, headers, HttpStatus.BAD_REQUEST, request);
    }

}
