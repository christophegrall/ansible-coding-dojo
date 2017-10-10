package com.dojo.services.personne.error.resource.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.context.MessageSource;
import org.springframework.validation.FieldError;

import java.io.IOException;

public class FieldErrorSerializer extends JsonSerializer<FieldError> {

    private MessageSource messageSource;

    public FieldErrorSerializer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void serialize(FieldError fieldError, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("objectName", fieldError.getObjectName());
        gen.writeStringField("field", fieldError.getField());
        gen.writeStringField("code", fieldError.getCode());
        gen.writeObjectField("rejectedValue", fieldError.getRejectedValue());
        gen.writeStringField("message", messageSource.getMessage(fieldError.getCode(), fieldError.getArguments(), fieldError.getDefaultMessage(), null));
        gen.writeEndObject();
    }
}
