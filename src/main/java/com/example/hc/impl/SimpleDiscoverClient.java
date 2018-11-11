package com.example.hc.impl;

import com.example.hc.core.DiscoveryClient;
import com.example.hc.core.LoggerBuddy;
import com.example.hc.core.RealServer;
import org.slf4j.Logger;

import java.util.List;

public class SimpleDiscoverClient implements DiscoveryClient {

    final static Logger LOGGER = LoggerBuddy.of(SimpleDiscoverClient.class);

    public List<RealServer> seek(String name) {
        return null;
    }
}
