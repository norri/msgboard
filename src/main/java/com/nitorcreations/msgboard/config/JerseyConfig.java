package com.nitorcreations.msgboard.config;

import org.glassfish.jersey.server.ResourceConfig;
import org.glassfish.jersey.server.spring.scope.RequestContextFilter;

public class JerseyConfig extends ResourceConfig {
    public JerseyConfig() {
        packages("com.nitorcreations.msgboard");
        register(RequestContextFilter.class);
    }
}
