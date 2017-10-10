package com.dojo.services.personne.config;

import com.dojo.services.personne.error.resource.serializer.FieldErrorSerializer;
import com.dojo.services.personne.error.resource.serializer.GlobalErrorSerializer;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.Module;
import com.fasterxml.jackson.databind.module.SimpleModule;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;

import javax.annotation.Resource;

@Configuration
public class JacksonConfig {

    @Resource
    private MessageSource messageSource;

    @Bean
    public Module exceptionModule() {
        SimpleModule module = new SimpleModule("ExceptionModule", new Version(1, 0, 0, "RELEASE", "com.manpower.services", "jackson-exception-module"));
        module.addSerializer(FieldError.class, new FieldErrorSerializer(messageSource));
        module.addSerializer(ObjectError.class, new GlobalErrorSerializer(messageSource));
        return module;
    }
}
