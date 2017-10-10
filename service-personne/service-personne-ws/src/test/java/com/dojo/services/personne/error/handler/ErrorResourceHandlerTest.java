package com.dojo.services.personne.error.handler;

import com.dojo.services.personne.Application;
import com.dojo.services.personne.error.exception.InvalidRequestException;
import com.dojo.services.personne.error.exception.ResourceNotFoundException;
import com.dojo.services.personne.util.TestUtil;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.SpringApplicationConfiguration;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.web.WebAppConfiguration;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.context.WebApplicationContext;

import javax.validation.Valid;

import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

/**
 * Created by sbert on 25/02/16.
 */
@RunWith(SpringJUnit4ClassRunner.class)
@SpringApplicationConfiguration(classes = Application.class)
@WebAppConfiguration
public class ErrorResourceHandlerTest {

    private MockMvc restTestMockMvc;

    @Autowired
    private WebApplicationContext wac;

    @Before
    public void initTest() {

        this.restTestMockMvc = MockMvcBuilders.webAppContextSetup(wac)
                .build();
    }

    @Test
    public void check_resource_not_found_exception_with_default_message() throws Exception {
        restTestMockMvc.perform(get("/test/resource/handler/controller/resource/not/found?typeMessage=DEFAULT"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("fake.code"))
                .andExpect(jsonPath("$.message").value("default message"))
                .andExpect(jsonPath("$.path").value("/test/resource/handler/controller/resource/not/found"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.ResourceNotFoundException"))
                .andExpect(jsonPath("$.globalErrors").doesNotExist())
                .andExpect(jsonPath("$.fieldErrors").doesNotExist());
    }

    @Test
    public void check_resource_not_found_exception_with_message_source() throws Exception {
        restTestMockMvc.perform(get("/test/resource/handler/controller/resource/not/found?typeMessage=CODE"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.test.resource.not.found.exception"))
                .andExpect(jsonPath("$.message").value("This is a resource not found exception"))
                .andExpect(jsonPath("$.path").value("/test/resource/handler/controller/resource/not/found"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.ResourceNotFoundException"))
                .andExpect(jsonPath("$.globalErrors").doesNotExist())
                .andExpect(jsonPath("$.fieldErrors").doesNotExist());
    }

    @Test
    public void check_resource_not_found_exception_with_message_source_and_rguments() throws Exception {
        restTestMockMvc.perform(get("/test/resource/handler/controller/resource/not/found?typeMessage=ARGUMENTS"))
                .andExpect(status().isNotFound())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.NOT_FOUND.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.NOT_FOUND.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.test.resource.not.found.exception.arguments"))
                .andExpect(jsonPath("$.message").value("This is a resource not found exception with arguments : 1, type"))
                .andExpect(jsonPath("$.path").value("/test/resource/handler/controller/resource/not/found"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.ResourceNotFoundException"))
                .andExpect(jsonPath("$.globalErrors").doesNotExist())
                .andExpect(jsonPath("$.fieldErrors").doesNotExist());
    }


    @Test
    public void check_invalid_request_exception_with_default_message() throws Exception {
        restTestMockMvc.perform(post("/test/resource/handler/controller/invalid/request")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new FakeModel("name", ResourceHandlerTestController.TypeMessage.DEFAULT))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("fake.code"))
                .andExpect(jsonPath("$.message").value("default message"))
                .andExpect(jsonPath("$.path").value("/test/resource/handler/controller/invalid/request"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.InvalidRequestException"))
                .andExpect(jsonPath("$.globalErrors").isArray())
                .andExpect(jsonPath("$.globalErrors..objectName").value("fakeModel"))
                .andExpect(jsonPath("$.globalErrors..code").value("fake.code.global"))
                .andExpect(jsonPath("$.globalErrors..message").value("default global message"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors..objectName").value("fakeModel"))
                .andExpect(jsonPath("$.fieldErrors..field").value("name"))
                .andExpect(jsonPath("$.fieldErrors..code").value("fake.code.field"))
                .andExpect(jsonPath("$.fieldErrors..rejectedValue").value("name"))
                .andExpect(jsonPath("$.fieldErrors..message").value("default field message"));
    }

    @Test
    public void check_invalid_request_exception_with_message_source() throws Exception {
        restTestMockMvc.perform(post("/test/resource/handler/controller/invalid/request")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new FakeModel("name", ResourceHandlerTestController.TypeMessage.CODE))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.test.invalid.request.exception"))
                .andExpect(jsonPath("$.message").value("This is an invalid request exception"))
                .andExpect(jsonPath("$.path").value("/test/resource/handler/controller/invalid/request"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.InvalidRequestException"))
                .andExpect(jsonPath("$.globalErrors").isArray())
                .andExpect(jsonPath("$.globalErrors..objectName").value("fakeModel"))
                .andExpect(jsonPath("$.globalErrors..code").value("fake.code.global"))
                .andExpect(jsonPath("$.globalErrors..message").value("default global message"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors..objectName").value("fakeModel"))
                .andExpect(jsonPath("$.fieldErrors..field").value("name"))
                .andExpect(jsonPath("$.fieldErrors..code").value("fake.code.field"))
                .andExpect(jsonPath("$.fieldErrors..rejectedValue").value("name"))
                .andExpect(jsonPath("$.fieldErrors..message").value("default field message"));
    }

    @Test
    public void check_invalid_request_exception_with_message_source_and_arguments() throws Exception {
        restTestMockMvc.perform(post("/test/resource/handler/controller/invalid/request")
                .contentType(TestUtil.APPLICATION_JSON_UTF8)
                .content(TestUtil.convertObjectToJsonBytes(new FakeModel("name", ResourceHandlerTestController.TypeMessage.ARGUMENTS))))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON_UTF8))
                .andExpect(jsonPath("$.timestamp").isNotEmpty())
                .andExpect(jsonPath("$.status").value(HttpStatus.BAD_REQUEST.value()))
                .andExpect(jsonPath("$.error").value(HttpStatus.BAD_REQUEST.getReasonPhrase()))
                .andExpect(jsonPath("$.code").value("error.test.invalid.request.exception.arguments"))
                .andExpect(jsonPath("$.message").value("This is an invalid request exception with arguments : 1, type"))
                .andExpect(jsonPath("$.path").value("/test/resource/handler/controller/invalid/request"))
                .andExpect(jsonPath("$.exception").value("com.dojo.services.personne.error.exception.InvalidRequestException"))
                .andExpect(jsonPath("$.globalErrors").isArray())
                .andExpect(jsonPath("$.globalErrors..objectName").value("fakeModel"))
                .andExpect(jsonPath("$.globalErrors..code").value("fake.code.global"))
                .andExpect(jsonPath("$.globalErrors..message").value("default global message"))
                .andExpect(jsonPath("$.fieldErrors").isArray())
                .andExpect(jsonPath("$.fieldErrors..objectName").value("fakeModel"))
                .andExpect(jsonPath("$.fieldErrors..field").value("name"))
                .andExpect(jsonPath("$.fieldErrors..code").value("fake.code.field"))
                .andExpect(jsonPath("$.fieldErrors..rejectedValue").value("name"))
                .andExpect(jsonPath("$.fieldErrors..message").value("default field message"));
    }


}

@RestController("/test/resource/handler/controller")
class ResourceHandlerTestController {

    public enum TypeMessage { DEFAULT, CODE, ARGUMENTS }

    @RequestMapping(name = "/resource/not/found", method = RequestMethod.GET, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void returnResourceNotFoundException(@RequestParam TypeMessage typeMessage) {
        switch (typeMessage) {
            case CODE:
                throw new ResourceNotFoundException("error.test.resource.not.found.exception", "default message");
            case ARGUMENTS:
                throw new ResourceNotFoundException("error.test.resource.not.found.exception.arguments", new Object[] {1, "type"},"default message");
            default:
                throw new ResourceNotFoundException("fake.code", "default message");
        }
    }

    @RequestMapping(name = "/invalid/request", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    public void returnInvalidRequestException(@Valid @RequestBody FakeModel fakeModel, BindingResult bindingResult) {
        bindingResult.rejectValue("name", "fake.code.field", "default field message");
        bindingResult.reject("fake.code.global", "default global message");
        switch (fakeModel.getTypeMessage()) {
            case CODE:
                throw new InvalidRequestException("error.test.invalid.request.exception", "default message", bindingResult);
            case ARGUMENTS:
                throw new InvalidRequestException("error.test.invalid.request.exception.arguments", new Object[] {1, "type"},"default message", bindingResult);
            default:
                throw new InvalidRequestException("fake.code", "default message", bindingResult);
        }
    }
}

class FakeModel {

    private String name;
    private ResourceHandlerTestController.TypeMessage typeMessage;

    public FakeModel() {
    }

    public FakeModel(String name, ResourceHandlerTestController.TypeMessage typeMessage) {
        this.name = name;
        this.typeMessage = typeMessage;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public ResourceHandlerTestController.TypeMessage getTypeMessage() {
        return typeMessage;
    }

    public void setTypeMessage(ResourceHandlerTestController.TypeMessage typeMessage) {
        this.typeMessage = typeMessage;
    }
}
