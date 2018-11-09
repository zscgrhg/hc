package com.example.hc;

import java.util.List;

public interface DiscoveryClient {
    List<RealServer> seek(String name);
}
