package com.dojo.services.personne.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.resource.ResourceTransformer;

import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import com.google.common.base.Predicate;

import static springfox.documentation.builders.PathSelectors.regex;
import static com.google.common.base.Predicates.not;
import static com.google.common.base.Predicates.and;



import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.ZonedDateTime;
import java.time.OffsetDateTime;
import java.util.Date;

@Configuration
@EnableSwagger2
public class Swagger2Config extends WebMvcConfigurerAdapter {


	 @Bean
	    public Docket personneApi() {
	        return new Docket(DocumentationType.SWAGGER_2)
	                .select()
	                    .apis(RequestHandlerSelectors.any())
	                    .paths(paths())
	                    .build()
	                .enableUrlTemplating(true);
	    }

	    private Predicate<String> paths() {
	        return and(
	                not(regex("/error.*")),
	                not(regex("/health.*")),
	                not(regex("/metrics.*")),
	                not(regex("/beans.*")),
	                not(regex("/configprops.*")),
	                not(regex("/dump.*")),
	                not(regex("/info.*")),
	                not(regex("/mappings.*")),
	                not(regex("/autoconfig.*")),
	                not(regex("/trace.*")),
	                not(regex("/env.*")),
	                not(regex("/liquibase.*")),
	                not(regex("/actuator.*")),
	                not(regex("/pause.*")),
	                not(regex("/refresh.*")),
	                not(regex("/resume.*")),
	                not(regex("/restart.*")),
	                not(regex("/logfile.*")),
	                not(regex("/heapdump.*")),
	                not(regex("/auditevents.*")),
	                not(regex("/loggers.*"))
	        );
	    }

	   
	    



    



}
