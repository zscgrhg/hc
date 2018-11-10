package com.example.hc.impl;

import com.example.hc.core.DiscoveryClient;
import com.example.hc.core.LoggerBuddy;
import com.example.hc.core.RealServer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

public class SimpleDiscoverClient implements DiscoveryClient {

    final static Logger LOGGER = LoggerBuddy.of(SimpleDiscoverClient.class);

    public List<RealServer> seek(String name) {
        return null;
    }

    public static void main(String[] args) {
        LOGGER.debug("OOPS");
    }


}
