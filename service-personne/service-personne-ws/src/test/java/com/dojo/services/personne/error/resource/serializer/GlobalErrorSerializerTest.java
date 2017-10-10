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
import org.springframework.validation.ObjectError;

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
public class GlobalErrorSerializerTest {

    @Resource
    private MessageSource messageSource;

    private ObjectMapper objectMapper;

    @Before
    public void initTest() {
        SimpleModule module = new SimpleModule("GlobalErrorModule", new Version(1, 0, 0, "RELEASE", "com.manpower.services", "jackson-global-error-module"));
        module.addSerializer(ObjectError.class, new GlobalErrorSerializer(messageSource));
        objectMapper = new ObjectMapper();
        objectMapper.registerModule(module);
    }

    @Test
    public void check_serialize_with_error_messages_properties() throws IOException {
        ObjectError globalError = new ObjectError("objectName", new String[] {"not_use_code", "error.test.global.error.message"}, new Object[] {1, "size"}, "default message");
        String jsonResult = objectMapper.writeValueAsString(globalError);

        assertThat((String) JsonPath.read(jsonResult, "$.objectName")).isNotNull().isEqualTo("objectName");
        assertThat((String) JsonPath.read(jsonResult, "$.code")).isNotNull().isEqualTo("error.test.global.error.message");
        assertThat((String) JsonPath.read(jsonResult, "$.message")).isNotNull().isEqualTo("Global error message");
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> paths = JsonPath.using(conf).parse(jsonResult).read("*");
        assertThat(paths)
                .hasSize(3)
                .containsExactly(
                        "$['objectName']",
                        "$['code']",
                        "$['message']"
                );
    }

    @Test
    public void check_serialize_with_error_messages_properties_and_arguments() throws IOException {
        ObjectError globalError = new ObjectError("objectName", new String[] {"not_use_code", "error.test.global.error.message.arguments"}, new Object[] {1, "size"}, "default message");
        String jsonResult = objectMapper.writeValueAsString(globalError);

        assertThat((String) JsonPath.read(jsonResult, "$.objectName")).isNotNull().isEqualTo("objectName");
        assertThat((String) JsonPath.read(jsonResult, "$.code")).isNotNull().isEqualTo("error.test.global.error.message.arguments");
        assertThat((String) JsonPath.read(jsonResult, "$.message")).isNotNull().isEqualTo("Global error message: 1, size");
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> paths = JsonPath.using(conf).parse(jsonResult).read("*");
        assertThat(paths)
                .hasSize(3)
                .containsExactly(
                        "$['objectName']",
                        "$['code']",
                        "$['message']"
                );
    }

    @Test
    public void check_serialize_without_error_messages_properties() throws IOException {
        ObjectError globalError = new ObjectError("objectName", new String[] {"not_use_code", "code.not.existing"}, new Object[] {1, "size"}, "default message");
        String jsonResult = objectMapper.writeValueAsString(globalError);

        assertThat((String) JsonPath.read(jsonResult, "$.objectName")).isNotNull().isEqualTo("objectName");
        assertThat((String) JsonPath.read(jsonResult, "$.code")).isNotNull().isEqualTo("code.not.existing");
        assertThat((String) JsonPath.read(jsonResult, "$.message")).isNotNull().isEqualTo("default message");
        Configuration conf = Configuration.builder().options(Option.AS_PATH_LIST).build();
        List<String> paths = JsonPath.using(conf).parse(jsonResult).read("*");
        assertThat(paths)
                .hasSize(3)
                .containsExactly(
                        "$['objectName']",
                        "$['code']",
                        "$['message']"
                );
    }

}
