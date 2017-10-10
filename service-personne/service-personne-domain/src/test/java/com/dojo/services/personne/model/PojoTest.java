package com.dojo.services.personne.model;

import com.openpojo.reflection.PojoClass;
import com.openpojo.reflection.filters.FilterClassName;
import com.openpojo.reflection.impl.PojoClassFactory;
import com.openpojo.validation.Validator;
import com.openpojo.validation.ValidatorBuilder;
import com.openpojo.validation.rule.impl.*;
import com.openpojo.validation.test.impl.GetterTester;
import com.openpojo.validation.test.impl.SetterTester;

import org.junit.Before;
import org.junit.Test;

import java.util.List;

public class PojoTest {

    private static final String POJO_PACKAGE_DOMAIN = "com.dojo.services.personne.model";

    private Validator validator;

    @Before
    public void setup() {

        validator = ValidatorBuilder.create()
                // Add Rules to validate structure for POJO_PACKAGE
                // See com.openpojo.validation.rule.impl for more ...
                .with(new GetterMustExistRule())
                .with(new SetterMustExistRule())
                .with(new NoPublicFieldsRule())
                .with(new NoPublicFieldsExceptStaticFinalRule())
                .with(new NoPrimitivesRule())
                .with(new NoStaticExceptFinalRule())
                .with(new NoFieldShadowingRule())
                .with(new NoNestedClassRule())
                .with(new SerializableMustHaveSerialVersionUIDRule())
                .with(new TestClassMustBeProperlyNamedRule())
                // Add Testers to validate behaviour for POJO_PACKAGE
                // See com.openpojo.validation.test.impl for more ...
                .with(new SetterTester())
                .with(new GetterTester())
                .build();

    }

    @Test
    public void testPojoStructureAndBehaviorForDomain() {
        List<PojoClass> pojoClasses = PojoClassFactory.getPojoClasses(POJO_PACKAGE_DOMAIN, new FilterClassName("^((?!Test$).)*$"));
        for (PojoClass pojoClass : pojoClasses) {
            validator.validate(pojoClasses);
        }
    }

}
