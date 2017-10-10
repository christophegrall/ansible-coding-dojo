package com.dojo.services.personne.error.resource.serializer;

import com.dojo.services.personne.Application;

import com.fasterxml.jackson.core.Version;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.module.SimpleModule;

import com.jayway.jsonpath.Configuration;
import com.jayway.jsonpath.JsonPath;
import com.jayway.jsonpath.Option;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.context.MessageSource;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.validation.FieldError;

import javax.annotation.Resource;

import java.io.IOException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

/**
 * Created by sbert on 22/02/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class FieldErrorSerializerTest {

    @Resource
    private MessageSource messageSource;

    private ObjectMapper objectMapper;

    @Before
    public void initTest() {
        SimpleModule module = new SimpleModule("FieldErrorModule", new Version(1, 0, 0, "RELEASE", "com.manpower.services", "jackson-field-error-module"));
        module.addSerializer(FieldError.class, new FieldErrorSerializer(messageSource));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    public void check_serialize_with_error_messages_properties() throws IOException {
        FieldError fieldError = new FieldError("objectName", "field", 1, false, new String[] {"not_use_code", "error.test.field.error.message"}, new Object[] {1, "size"}, "default message");
        String jsonResult = objectMapper.writeValueAsString(fieldError);

        assertThat((String) JsonPath.read(jsonResult, "$.objectName")).isNotNull().isEqualTo("objectName");
        assertThat((String) JsonPath.read(jsonResult, "$.field")).isNotNull().isEqualTo("field");
        assertThat((String) JsonPath.read(jsonResult, "$.code")).isNotNull().isEqualTo("error.test.field.error.message");
        assertThat((Integer) JsonPath.read(jsonResult, "$.rejectedValue")).isNotNull().isEqualTo(1);
        assertThat((String) JsonPath.read(jsonResult, "$.message")).isNotNull().isEqualTo("Field error message");
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> paths = JsonPath.using(conf).parse(jsonResult).read("*");
        assertThat(paths)
                .hasSize(5)
                .containsExactly(
                        "$['objectName']",
                        "$['field']",
                        "$['code']",
                        "$['rejectedValue']",
                        "$['message']"
                );
    }

    @Test
    public void check_serialize_with_error_messages_properties_and_arguments() throws IOException {
        FieldError fieldError = new FieldError("objectName", "field", 1, false, new String[] {"not_use_code", "error.test.field.error.message.arguments"}, new Object[] {1, "size"}, "default message");
        String jsonResult = objectMapper.writeValueAsString(fieldError);

        assertThat((String) JsonPath.read(jsonResult, "$.objectName")).isNotNull().isEqualTo("objectName");
        assertThat((String) JsonPath.read(jsonResult, "$.field")).isNotNull().isEqualTo("field");
        assertThat((String) JsonPath.read(jsonResult, "$.code")).isNotNull().isEqualTo("error.test.field.error.message.arguments");
        assertThat((Integer) JsonPath.read(jsonResult, "$.rejectedValue")).isNotNull().isEqualTo(1);
        assertThat((String) JsonPath.read(jsonResult, "$.message")).isNotNull().isEqualTo("Field error message: 1, size");
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> paths = JsonPath.using(conf).parse(jsonResult).read("*");
        assertThat(paths)
                .hasSize(5)
                .containsExactly(
                        "$['objectName']",
                        "$['field']",
                        "$['code']",
                        "$['rejectedValue']",
                        "$['message']"
                );
    }

    @Test
    public void check_serialize_without_error_messages_properties() throws IOException {
        FieldError fieldError = new FieldError("objectName", "field", 1, false, new String[] {"not_use_code", "code.not.existing"}, new Object[] {1, "size"}, "default message");
        String jsonResult = objectMapper.writeValueAsString(fieldError);

        assertThat((String) JsonPath.read(jsonResult, "$.objectName")).isNotNull().isEqualTo("objectName");
        assertThat((String) JsonPath.read(jsonResult, "$.field")).isNotNull().isEqualTo("field");
        assertThat((String) JsonPath.read(jsonResult, "$.code")).isNotNull().isEqualTo("code.not.existing");
        assertThat((Integer) JsonPath.read(jsonResult, "$.rejectedValue")).isNotNull().isEqualTo(1);
        assertThat((String) JsonPath.read(jsonResult, "$.message")).isNotNull().isEqualTo("default message");
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> paths = JsonPath.using(conf).parse(jsonResult).read("*");
        assertThat(paths)
                .hasSize(5)
                .containsExactly(
                        "$['objectName']",
                        "$['field']",
                        "$['code']",
                        "$['rejectedValue']",
                        "$['message']"
                );
    }

}
