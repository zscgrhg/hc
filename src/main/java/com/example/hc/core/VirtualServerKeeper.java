package com.example.hc.core;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

public class VirtualServerKeeper {
    private static Map<String, VirtualServer> SERVERS = new ConcurrentHashMap<String, VirtualServer>();

    public synchronized static VirtualServer create(String name, DiscoveryClient discoveryClient, LoadBlancer loadBlancer, HacExecutor hacExecutor) {
        if (SERVERS.containsKey(name)) {
            return SERVERS.get(name);
        } else {
            VirtualServer created = new VirtualServer(name, discoveryClient,
                    loadBlancer,
                    hacExecutor);
            SERVERS.put(name, created);
            return created;
        }
    }

    public synchronized static VirtualServer create(String name, DiscoveryClient discoveryClient) {
        if (SERVERS.containsKey(name)) {
            return SERVERS.get(name);
        } else {
            VirtualServer created = new VirtualServer(name, discoveryClient,
                    new SimpleLB(),
                    new HacExecutor(new SharedHacCustom()));
            SERVERS.put(name, created);
            return created;
        }
    }
}
