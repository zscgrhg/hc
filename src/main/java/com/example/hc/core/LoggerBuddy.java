package com.example.hc.core;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.example.hc.impl.SimpleDiscoverClient;
import org.slf4j.Logger;

public class LoggerBuddy {
    public static final LoggerContext LOGGER_CONTEXT = new LoggerContext();
    public static final String CONFIG_NAME = "hc.xml";

    public static Logger of(Class<?> clazz) {
        return LOGGER_CONTEXT.getLogger(clazz);
    }

    static {
        try {
            JoranConfigurator configurator = new JoranConfigurator();
            LOGGER_CONTEXT.reset();
            configurator.setContext(LOGGER_CONTEXT);
            configurator.doConfigure(SimpleDiscoverClient.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_NAME));
        } catch (JoranException je) {

        }
    }
}
