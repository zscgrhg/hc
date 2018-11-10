package com.example.hc.core;

import ch.qos.logback.classic.LoggerContext;
import ch.qos.logback.classic.joran.JoranConfigurator;
import ch.qos.logback.core.joran.spi.JoranException;
import com.example.hc.impl.SimpleDiscoverClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class LoggerBuddy {
    public static final String CONFIG_NAME="hc.xml";
    public static Logger of(Class<?> clazz) {
        return LoggerFactory.getLogger(clazz);
    }

    static {
        System.out.println("reset");
        LoggerContext context = (LoggerContext) LoggerFactory.getILoggerFactory();

        try {
            JoranConfigurator configurator = new JoranConfigurator();
            configurator.setContext(context);
            context.reset();
            configurator.doConfigure(SimpleDiscoverClient.class
                    .getClassLoader()
                    .getResourceAsStream(CONFIG_NAME));
        } catch (JoranException je) {

        }
    }
}
