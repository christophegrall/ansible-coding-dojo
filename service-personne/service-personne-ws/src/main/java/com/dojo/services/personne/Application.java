package com.dojo.services.personne;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.jpa.convert.threeten.Jsr310JpaConverters;

import com.dojo.services.personne.config.Swagger2Config;

/**
 * Created by sbert on 01/08/15.
 */
@SpringBootApplication
public class Application {

    public static void main(String[] args) {
        SpringApplication.run(new Class[]{ Application.class, Swagger2Config.class }, args);
    }

}
