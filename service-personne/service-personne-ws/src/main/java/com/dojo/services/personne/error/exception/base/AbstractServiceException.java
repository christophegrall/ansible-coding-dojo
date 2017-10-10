package com.dojo.services.personne.error.exception.base;

import java.util.Optional;

public abstract class AbstractServiceException extends RuntimeException {

    private String code;
    private String name;
    private Object[] arguments;

    public AbstractServiceException(String code, String defaultMessage) {
        super(defaultMessage);
        this.name = getClass().getName();
        this.code = code;
    }

    public AbstractServiceException(String code, Object[] arguments, String defaultMessage) {
        super(defaultMessage);
        this.name = getClass().getName();
        this.code = code;
        this.arguments = Optional.ofNullable(arguments)
                // don't use method reference for cloning array due to JVM bug with version < 1.8.0_41 : https://bugs.openjdk.java.net/browse/JDK-8056051
                .map(a -> a.clone())
                .orElse(null);
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public Object[] getArguments() {
        return this.arguments = Optional.ofNullable(arguments)
                // don't use method reference for cloning array due to JVM bug with version < 1.8.0_41 : https://bugs.openjdk.java.net/browse/JDK-8056051
                .map(a -> a.clone())
                .orElse(null);
    }
}
