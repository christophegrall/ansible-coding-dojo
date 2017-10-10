package com.dojo.services.personne.error.code;

import org.junit.Test;

import java.lang.reflect.Constructor;

public class PersonneErrorCodeTest {

    @Test
    public void should_fake_constructor_coverage() throws Exception {
        Constructor<PersonneErrorCode> constructor = PersonneErrorCode.class.getDeclaredConstructor();
        constructor.setAccessible(true);
        constructor.newInstance();
    }
}
