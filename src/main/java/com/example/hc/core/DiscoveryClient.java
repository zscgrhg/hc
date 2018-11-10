package com.example.hc.core;

import java.util.List;

public interface DiscoveryClient {
    List<RealServer> seek(String name);
}
