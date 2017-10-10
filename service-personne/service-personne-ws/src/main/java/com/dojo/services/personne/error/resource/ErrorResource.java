package com.dojo.services.personne.error.resource;

import com.dojo.services.personne.error.exception.base.AbstractServiceException;

import com.fasterxml.jackson.annotation.JsonInclude;

import org.springframework.http.HttpStatus;
import org.springframework.validation.*;
import org.springframework.web.context.request.WebRequest;

import java.time.ZonedDateTime;
import java.util.List;

@JsonInclude(JsonInclude.Include.NON_NULL)
public class ErrorResource {

    private ZonedDateTime timestamp;
    private int status;
    private String error;
    private String code;
    private String message;
    private String path;
    private String exception;
    private List<ObjectError> globalErrors;
    private List<FieldError> fieldErrors;

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                 CONSTRUCTOR                                                                //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ErrorResource() {
        this.timestamp = ZonedDateTime.now();
    }

    public <T extends AbstractServiceException> ErrorResource(T exception, HttpStatus httpStatus, WebRequest request) {
        this.timestamp = ZonedDateTime.now();
        this.status = httpStatus.value();
        this.error = httpStatus.getReasonPhrase();
        this.code = exception.getCode();
        this.exception = exception.getName();
        this.path = request.getDescription(false).substring(4);
    }

    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////
    //                                  GETTER & SETTER                                                           //
    ////////////////////////////////////////////////////////////////////////////////////////////////////////////////

    public ZonedDateTime getTimestamp() {
        return timestamp;
    }

    public int getStatus() {
        return status;
    }

    public void setStatus(int status) {
        this.status = status;
    }

    public String getError() {
        return error;
    }

    public void setError(String error) {
        this.error = error;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getPath() {
        return path;
    }

    public void setPath(String path) {
        this.path = path;
    }

    public String getException() {
        return exception;
    }

    public void setException(String exception) {
        this.exception = exception;
    }

    public List<ObjectError> getGlobalErrors() {
        return globalErrors;
    }

    public void setGlobalErrors(List<ObjectError> globalErrors) {
        this.globalErrors = globalErrors;
    }

    public List<FieldError> getFieldErrors() {
        return fieldErrors;
    }

    public void setFieldErrors(List<FieldError> fieldErrors) {
        this.fieldErrors = fieldErrors;
    }
}
