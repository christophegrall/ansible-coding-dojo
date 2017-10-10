package com.dojo.services.personne.error.resource.serializer;

import com.fasterxml.jackson.core.JsonGenerator;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonSerializer;
import com.fasterxml.jackson.databind.SerializerProvider;

import org.springframework.context.MessageSource;
import org.springframework.validation.ObjectError;

import java.io.IOException;

public class GlobalErrorSerializer extends JsonSerializer<ObjectError> {

    private MessageSource messageSource;

    public GlobalErrorSerializer(MessageSource messageSource) {
        this.messageSource = messageSource;
    }

    @Override
    public void serialize(ObjectError objectError, JsonGenerator gen, SerializerProvider serializers) throws IOException, JsonProcessingException {
        gen.writeStartObject();
        gen.writeStringField("objectName", objectError.getObjectName());
        gen.writeStringField("code", objectError.getCode());
        gen.writeStringField("message", messageSource.getMessage(objectError.getCode(), objectError.getArguments(), objectError.getDefaultMessage(), null));
        gen.writeEndObject();
    }
}
