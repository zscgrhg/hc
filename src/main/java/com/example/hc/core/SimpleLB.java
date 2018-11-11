package com.example.hc.core;

import java.util.List;

public class SimpleLB implements LoadBlancer {
    public RealServer select(List<RealServer> list) {
        return list.get(0);
    }
}
